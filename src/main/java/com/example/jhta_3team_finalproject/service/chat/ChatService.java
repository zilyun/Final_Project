package com.example.jhta_3team_finalproject.service.chat;



import com.example.jhta_3team_finalproject.cache.RedisUtils;
import com.example.jhta_3team_finalproject.domain.User.User;
import com.example.jhta_3team_finalproject.domain.chat.ChatMessage;
import com.example.jhta_3team_finalproject.domain.chat.ChatParticipate;
import com.example.jhta_3team_finalproject.domain.chat.ChatRoom;
import com.example.jhta_3team_finalproject.mybatis.mapper.chat.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMapper dao;
    private final RedisUtils redisUtils;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    LocalTime localTime = LocalTime.of(0, 0, 0);
    LocalDate localDate = LocalDate.now(); // 오늘 0시 0분 0초

    public ChatMessage createMessage(ChatMessage chatMessage) throws Exception {
        int result = dao.createMessage(chatMessage);
        if (result > 0) {
            /**
             * 2024-06-05, Insert 가 성공적으로 된 경우 Redis 에도 저장하기 위해 마지막 메시지를 가져옴
             */
            chatMessage = dao.lastMessage();
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            String roomKey = String.valueOf(chatMessage.getChatRoomNum());
            String dateKey = simpleDateFormat.format(date.getTime());
            String redisKey = roomKey + ":" + dateKey; // 방번호:날짜
            Long expiredTime = 1L; // 만료 시간 1주일 부여
            redisUtils.setAddSets(redisKey, chatMessage); // 키, 값
            redisUtils.setExpired(redisKey, expiredTime);
        }
        return chatMessage; // 마지막 메시지를 반환
    }

    public int updateMsgImageUrl(ChatMessage chatMessage) throws Exception {
        /**
         * 2024-06-07, Redis 에서 Update 가 필요한 oldChatMessage 를 저장해놓습니다.
         */
        ChatMessage oldChatMessage = dao.searchOldMessage(chatMessage);
        int result = dao.updateMsgImageUrl(chatMessage);
        if (result > 0) {
            /**
             * 2024-06-07, S3의 URL 이 정상적으로 Update 된 경우 Redis도 oldChatMessage를 newMessage로 Update
             */
            ChatMessage newChatMessage = dao.searchNewMessage(oldChatMessage);
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            String roomKey = String.valueOf(chatMessage.getChatRoomNum());
            String dateKey = simpleDateFormat.format(date.getTime());
            String redisKey = roomKey + ":" + dateKey; // 방번호:날짜
            Long expiredTime = 1L; // 만료 시간 1주일 부여
            redisUtils.setUpdateSets(redisKey, oldChatMessage, newChatMessage); // 키, old value, new value
            redisUtils.setExpired(redisKey, expiredTime);
        }
        return result;
    }
    public int createChatRoom(ChatRoom chatRoom, String chatInviteUserList) throws Exception {
        int result = dao.createChatRoom(chatRoom);
        chatRoom = dao.lastChatRoom();

        /**
         * 2024-06-10, 자신의 정보도 채팅방 참여 테이블에 등록
         */
        ChatParticipate chatParticipate = new ChatParticipate();
        chatParticipate.setChatRoomNum(chatRoom.getChatRoomNum());
        chatParticipate.setChatUserId(chatRoom.getChatSessionId());
        dao.addChatParticipate(chatParticipate);

        /**
         * 2024-06-10, 채팅방을 생성하기 전 초대된 유저들을 관리할 수 있는 채팅 참여 테이블에 등록
         */
        String[] chatInviteUserArray = chatInviteUserList.split(",");
        for(String inviteUserId : chatInviteUserArray) {
            chatParticipate = new ChatParticipate();
            chatParticipate.setChatRoomNum(chatRoom.getChatRoomNum());
            chatParticipate.setChatUserId(inviteUserId);
            dao.addChatParticipate(chatParticipate);
        }

        return result;
    }

    public List<ChatMessage> searchMessages(ChatMessage chatMessage) throws Exception {
        return dao.searchMessages(chatMessage);
    }

    public List<ChatRoom> searchRoom(ChatRoom chatRoom) throws Exception {
        return dao.searchRoom(chatRoom);
    }

    public List<ChatRoom> searchRoomUser(ChatRoom chatRoom) throws Exception {
        return dao.searchRoomUser(chatRoom);
    }

    public List<User> chatUserList(String chatUserId) {
        return dao.chatUserList(chatUserId);
    }

    public User chatUserProfile(String chatUserId) {
        return dao.chatUserProfile(chatUserId);
    }

    public int chatUserProfileMsgUpdate(String profileStatusMsg, String chatUserId) {
        User user = new User();
        user.setUserChatStatusMsg(profileStatusMsg);
        user.setUserId(chatUserId);
        return dao.chatUserProfileMsgUpdate(user);
    }
}