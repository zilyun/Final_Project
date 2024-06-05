package com.example.jhta_3team_finalproject.service.chat;

import com.example.jhta_3team_finalproject.cache.RedisUtils;
import com.example.jhta_3team_finalproject.domain.chat.ChatMessage;
import com.example.jhta_3team_finalproject.mybatis.mapper.chat.ChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

    private final ChatMapper dao;
    private final RedisUtils redisUtils;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    LocalTime localTime = LocalTime.of(0, 0, 0);

    /**
     * 2024-06-05, 1주일 전 데이터를 하루씩 가져와서 통합 리스트에 담기
     */
    public List<ChatMessage> getRedisChatMessage(ChatMessage chatMessage) {
        List<ChatMessage> oneWeekTotalList = new ArrayList<>();
        long num = chatMessage.getChatRoomNum();
        for (int dayCount = 7; dayCount >= 0; dayCount--) { // 오늘부터 1주일 전 기록까지 반복하여 하루씩 처리
            LocalDate localDate = LocalDate.now().minusDays(dayCount);
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            String dateStr = simpleDateFormat.format(date.getTime());
            String key = num + ":" + dateStr;
            List<ChatMessage> oneDayList = getChatMessageList(num, key, dateStr);
            oneWeekTotalList.addAll(oneDayList);
        }
        return oneWeekTotalList;
    }

    /**
     * 2024-06-05
     * true, 키가 있다면 레디스에서 가져와서 return
     * false, 키가 없다면 레디스에 올려준 후 해당 List 는 바로 return
     */
    private List<ChatMessage> getChatMessageList(long num, String key, String dateStr) {
        if (redisUtils.isKeyExists(key)) {
            log.info("RedisGet");
            Set<ChatMessage> chatMessageSet = redisUtils.getSets(key);
            List<ChatMessage> chatMessageList = new ArrayList<>(chatMessageSet);
            chatMessageList.sort(Comparator.comparing(ChatMessage::getMessageNum));
            return chatMessageList;
        } else {
            log.info("RdbGet");
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setChatRoomNum(num);
            chatMessage.setDateStr(dateStr);
            List<ChatMessage> chatMessageList = dao.redisSearchMessages(chatMessage);
            if (!chatMessageList.isEmpty()) {
                chatMessageList.forEach(chatMsg -> {
                    String roomKey = String.valueOf(chatMsg.getChatRoomNum());
                    String dateKey = simpleDateFormat.format(chatMsg.getSendTime());
                    String redisKey = roomKey + ":" + dateKey; // 방번호:날짜
                    Long expiredTime = 1L; // 만료 시간 1주일 부여
                    redisUtils.setInitSets(redisKey, chatMsg); // 키, 값
                    redisUtils.setExpired(redisKey, expiredTime);
                });
            }
            return chatMessageList;
        }
    }
}