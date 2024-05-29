package com.example.jhta_3team_finalproject.controller;

import com.example.jhta_3team_finalproject.domain.Table.Board;
import com.example.jhta_3team_finalproject.domain.Table.Table_Files;
import com.example.jhta_3team_finalproject.service.table.BoardService;
import com.example.jhta_3team_finalproject.service.table.TableCommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.io.*;
import java.util.Random;

@Controller
@RequestMapping(value = "/table")
public class TableController {

    private static final Logger logger = LoggerFactory.getLogger(TableController.class);

    @Value("${my.savefolder}")
    private String saveFolder;
    private BoardService BS;
    private TableCommentService CS;

    @Autowired
    public TableController(BoardService BS, TableCommentService CS) {
        this.BS = BS;
        this.CS = CS;
    }

    // 리스트 가져오기
    @RequestMapping(value = "/freelist", method = RequestMethod.GET)
    public ModelAndView freeTable(@RequestParam(value = "page", defaultValue = "1") int page,
                                  ModelAndView mv) {

        int limit = 10; // 한 화면에 출력할 로우 갯수
        int listcount = BS.getListCount(); // 총 리스트 수를 받아온다.

        // 총 페이지 수
        int maxpage = (listcount + limit - 1) / limit;

        // 현재 페이지에 보여줄 시작 페이지 수
        int startpage = ((page - 1) / 10) * 10 + 1;
        // 현재 페이지에 보여줄 마지막 페이지 수
        int endpage = startpage + 10 - 1;

        if (endpage > maxpage)
            endpage = maxpage;

        List<Board> boardlist = BS.getBoardList(page, limit); // 리스트를 받아옴

        mv.setViewName("table/Free_table");
        mv.addObject("page", page);
        mv.addObject("maxpage", maxpage);
        mv.addObject("startpage", startpage);
        mv.addObject("endpage", endpage);
        mv.addObject("listcount", listcount);
        mv.addObject("boardlist", boardlist);
        mv.addObject("limit", limit);

        return mv;

    }

    // 글쓰기 폼 불러오기
    @GetMapping("/f_write")
    public String wirteForm() {
        return "table/Fwrite";
    }

    // 작성 글 DB 등록
    @PostMapping("/add")
    public String add(Board board, HttpServletRequest request,
                      @RequestParam("uploadfile[]") MultipartFile[] uploadfiles)
            throws Exception {

        // Board 객체를 먼저 저장하고, BOARD_NUM을 받아옵니다.
        BS.insertBoard(board); // Board 객체 저장
        int boardNum = board.getBOARD_NUM(); // 저장된 BOARD_NUM 가져오기

        List<Table_Files> files = new ArrayList<>();
        for (MultipartFile uploadfile : uploadfiles) {
            if (!uploadfile.isEmpty()) {
                String fileName = uploadfile.getOriginalFilename(); // 원래 파일명

                String fileDBName = fileDBName(fileName, saveFolder);
                logger.info("fileDBName = " + fileDBName);

                uploadfile.transferTo(new File(saveFolder + fileDBName));
                logger.info("transferTo path = " + saveFolder + fileDBName);

                Table_Files file = new Table_Files();
                file.setOriginal_file_name(fileName);
                file.setFile_name(fileDBName);
                files.add(file);


            }
        }

        BS.insertFile(boardNum, files); // 저장메서드 호출
        logger.info(board.toString()); //selectKey로 정의한 BOARD_NUM 값 확인
        return "redirect:freelist";

    }

    // 서버에 파일 생성/파일 이름 생성 로직
    private String fileDBName(String fileName, String saveFolder) {
        // 새로운 폴더 이름 : 오늘 년+월+일
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);            //오늘 년도
        int month = c.get(Calendar.MONTH) + 1;    //오늘 월
        int date = c.get(Calendar.DATE);            //오늘 일

        String homedir = saveFolder + "/" + year + "-" + month + "-" + date;
        logger.info(homedir);
        File path1 = new File(homedir);
        if (!(path1.exists())) {
            path1.mkdirs(); // 새로운 폴더 생성
        }

        // 난수 획득
        Random r = new Random();
        int random = r.nextInt(100000000);

        // 확장자 구하기 시작
        int index = fileName.lastIndexOf(".");
        // 문자열에서 특정 문자열의 위치 값(index) 반환
        // indexOf가 처음 발견되는 문자열에 대한 Index를 반환하는 반면,
        // lastIndexOf는 마지막으로 발견되는 문자열의 Index를 반환
        // (파일명에 점이 여러개 있을 경우 맨 마지막에 발견되는 문자열의 위치를 리턴한다.
        logger.info("index = " + index);

        String fileExtension = fileName.substring(index + 1);
        logger.info("fileExtension = " + fileExtension);
        // 확장자 구하기 끝

        // 새로운 파일명
        String refileName = "bbs" + year + month + date + random + "." + fileExtension;
        logger.info("refileName = " + refileName);

        // MySQL 디비에 저장될 파일 명
        // String fileDBName = "/" + year + "-" month + "-" + date + "/" + refileName;
        String fileDBName = File.separator + year + "-" + month + "-" + date
                + File.separator + refileName;
        logger.info("fileDBName = " + fileDBName);

        return fileDBName;
    }

    @GetMapping("/detail")
    public ModelAndView Detail(
            int num, ModelAndView mv,
            HttpServletRequest request,
            @RequestHeader(value = "referer", required = false) String beforeURL) {
		/*
			1. String BeforeURL = request.getHeader("referer"); 의미로
			   어느 주소에서 Detail로 이동했는지 header의 정보 중 "referer"을 통해 알 수 있다.
			2. 수정 후 이곳으로 이동하는 경우 조회수는 증가하지 않도록 한다.
			3. myhome4/board/list에서 제목을 클릭한 경우 조회수가 증가하도록 한다.
		 */

        logger.info("referer : " + beforeURL);
        if (beforeURL != null && beforeURL.endsWith("list")) {
            BS.setReadCountUpdate(num);
        }

        Board board = BS.getDetail(num);
        // board = null; // error페이지 이동 확인하고자 임의로 지정.
        if (board == null) {
            logger.info("상세보기 실패");
            mv.setViewName("error/error");
            mv.addObject("url", request.getRequestURL());
            mv.addObject("message", "실패하였습니다.");
        } else {
            logger.info("상세보기 성공");
            int count = CS.getListCount(num);
            mv.setViewName("table/Fview");
            mv.addObject("count", count);
            mv.addObject("boarddata", board);
        }
        return mv;
    }

    @GetMapping("/t")
    public String test_table() {
        return "/table/basic-table_backup";
    }
}
