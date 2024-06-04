package com.example.jhta_3team_finalproject.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cal")
public class CalendarController {

    private final HttpServletResponse httpServletResponse;

    public CalendarController(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String logout() {
        return "calendar/blank-page";
    }

    @ResponseBody
    @RequestMapping(value = "/getjson", method = RequestMethod.GET)
    public String json() {
        String text = """
      [{
        "_id": 1,
                                           "title": "거래처 미팅",
                                           "description": "Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.",
                                           "start": "2019-05-07T09:30",
                                           "end": "2019-05-07T15:00",
                                           "type": "카테고리1",
                                           "username": "다현",
                                           "backgroundColor": "#D25565",
                                           "textColor": "#ffffff",
                                           "allDay": false
                                         }, {
                                           "_id": 2,
                                           "title": "치과예약",
                                           "description": "Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.",
                                           "start": "2019-05-01T12:30",
                                           "end": "2019-05-01T15:30",
                                           "type": "카테고리1",
                                           "username": "나연",
                                           "backgroundColor": "#9775fa",
                                           "textColor": "#ffffff",
                                           "allDay": false
                                         }, {
                                           "_id": 3,
                                           "title": "철수 생일",
                                           "description": "Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.",
                                           "start": "2019-05-12",
                                           "end": "2019-05-12",
                                           "type": "카테고리2",
                                           "username": "다현",
                                           "backgroundColor": "#ffa94d",
                                           "textColor": "#ffffff",
                                           "allDay": true
                                         }, {
                                           "_id": 4,
                                           "title": "멜론 만기",
                                           "description": "Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.",
                                           "start": "2019-05-06",
                                           "end": "2019-05-06",
                                           "type": "카테고리2",
                                           "username": "지효",
                                           "backgroundColor": "#74c0fc",
                                           "textColor": "#ffffff",
                                           "allDay": true
                                         }, {
                                           "_id": 5,
                                           "title": "청약 입금",
                                           "description": "Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.",
                                           "start": "2019-05-08",
                                           "end": "2019-05-08",
                                           "type": "카테고리3",
                                           "username": "지효",
                                           "backgroundColor": "#f06595",
                                           "textColor": "#ffffff",
                                           "allDay": true
                                         }, {
                                           "_id": 6,
                                           "title": "카드값 납부",
                                           "description": "Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.",
                                           "start": "2019-05-14",
                                           "end": "2019-05-14",
                                           "type": "카테고리2",
                                           "username": "사나",
                                           "backgroundColor": "#63e6be",
                                           "textColor": "#ffffff",
                                           "allDay": true
                                         }, {
                                           "_id": 7,
                                           "title": "휴가",
                                           "description": "Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.",
                                           "start": "2019-05-18",
                                           "end": "2019-05-20",
                                           "type": "카테고리4",
                                           "username": "사나",
                                           "backgroundColor": "#a9e34b",
                                           "textColor": "#ffffff",
                                           "allDay": true
                                         },{
                                           "_id": 8,
                                           "title": "세차예약",
                                           "description": "Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.",
                                           "start": "2019-05-24T09:00",
                                           "end": "2019-05-24T10:00",
                                           "type": "카테고리3",
                                           "username": "정연",
                                           "backgroundColor": "#4d638c",
                                           "textColor": "#ffffff",
                                           "allDay": false
                                         },{
                                           "_id": 9,
                                           "title": "출장",
                                           "description": "Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.",
                                           "start": "2019-05-28",
                                           "end": "2019-05-31",
                                           "type": "카테고리3",
                                           "username": "정연",
                                           "backgroundColor": "#495057",
                                           "textColor": "#ffffff",
                                           "allDay": true
                                         },{
                                           "_id": 10,
                                           "title": "접수 기간",
                                           "description": "Lorem ipsum dolor sit incid idunt ut Lorem ipsum sit.",
                                           "start": "2019-05-15",
                                           "end": "2019-05-22",
                                           "type": "카테고리2",
                                           "username": "다현",
                                           "backgroundColor": "#9775fa",
                                           "textColor": "#ffffff",
                                           "allDay": true
      }]
""";
        return text;
    }

}
