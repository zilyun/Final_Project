package com.example.jhta_3team_finalproject.service.customer;

import com.example.jhta_3team_finalproject.domain.inquery.InqueryBoard;
import com.example.jhta_3team_finalproject.mybatis.mapper.customer.InqueryBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class InqueryServiceImpl implements InqueryService {

    private InqueryBoardMapper dao;

    @Autowired
    public InqueryServiceImpl(InqueryBoardMapper dao) {
        this.dao = dao;
    }

    @Override
    public int getListCount() {
        return dao.getListCount();
    }

    @Override
    public void insertBoard(InqueryBoard inqueryBoard) {
        dao.insertBoard(inqueryBoard);
    }

    @Override
    //@Cacheable(value = "inqueryBoardPage")
    public List<InqueryBoard> getBoardList(int page, int limit) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        //Oracle
        //int startrow = (page - 1) * limit + 1;
        //int endrow = startrow + limit - 1;
        //Mysql
        int startrow = (page - 1) * limit;
        int endrow = startrow + limit;
        map.put("start", startrow);
        map.put("end", endrow);

        List<InqueryBoard> templist = dao.getBoardList(map);
        List<InqueryBoard> list = new ArrayList<>();

        // 2024-04-04 글 비밀번호가 null이 아니면 true, 아니면 false
        templist.forEach(inqueryBoard -> {
            if (inqueryBoard.getInqPass() == null) {
                inqueryBoard.setInqPassExist(false);
            } else {
                inqueryBoard.setInqPassExist(!(inqueryBoard.getInqPass().isEmpty()));
            }
            list.add(inqueryBoard);
        });
        return list;
    }

    @Override
    public InqueryBoard getDetail(int num) {
        InqueryBoard inqueryBoard = dao.getDetail(num);
        inqueryBoard.setInqPassExist(inqueryBoard.getInqPass() != null && !inqueryBoard.getInqPass().isEmpty());
        return inqueryBoard;
    }

    @Override
    public int setReadCountUpdate(int num) {
        return dao.setReadCountUpdate(num);
    }

    @Override
    public boolean isBoardWriter(int num, String pass) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("num", num);
        map.put("pass", pass);
        InqueryBoard result = dao.isBoardWriter(map);
        if (result == null)
            return false;
        else
            return true;
    }

    @Override
    public int boardModify(InqueryBoard inqueryBoard) {
        return dao.boardModify(inqueryBoard);
    }

    @Transactional
    @Override
    public int boardDelete(int num) {
        int result = 0;
        InqueryBoard inqueryBoard = dao.getDetail(num);
        if (inqueryBoard != null) {
            result = dao.boardDelete(inqueryBoard);
        }
        return result;
    }

    @Override
    public List<String> getDeleteFileList() {
        return dao.getDeleteFileList();
    }

    @Override
    public void deleteFileList(String filename) {
        dao.deleteFileList(filename);
    }

    @Override
    public boolean isBoardPassNull(int num) {
        InqueryBoard result = dao.isBoardPassNull(num);

        if (result.getInqPass().equals(""))
            return true;
        else
            return false;
    }

}
