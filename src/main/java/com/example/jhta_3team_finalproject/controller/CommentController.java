package com.example.jhta_3team_finalproject.controller;

import com.example.jhta_3team_finalproject.domain.Table.Comment;
import com.example.jhta_3team_finalproject.service.table.TableCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private TableCommentService CS;

    @Autowired
    public CommentController(TableCommentService CS) {
        this.CS = CS;
    }

    @PostMapping(value = "/list")
    public Map<String, Object> CommentList(int board_num, int page) {
        logger.info("/list : board_num = "+board_num);

        List<Comment> list = CS.getCommentList(board_num, page);

        int listcount = CS.getListCount(board_num);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", list);
        map.put("listcount", listcount);
        logger.info("map=" + map.toString());
        logger.info("/comment/list");
        return map;
    }

    @PostMapping(value = "/add")
    public int CommentAdd(Comment co) {
        logger.info(co.toString());
        logger.info("등록실행!");
        return CS.commentsInsert(co);
    }

}
