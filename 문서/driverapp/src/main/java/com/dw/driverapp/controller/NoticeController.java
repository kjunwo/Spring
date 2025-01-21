package com.dw.driverapp.controller;

import com.dw.driverapp.exception.UnauthorizedUserException;
import com.dw.driverapp.model.Notice;
import com.dw.driverapp.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    // 유저- 공지사항 전체를 조회
    @GetMapping("/notice/all")
    public ResponseEntity<List<Notice>> getAllNotice(){
        return new ResponseEntity<>(noticeService.getAllNotice(), HttpStatus.OK);
    }
    // 유저- 공지사항을 id로 조회
    @GetMapping("/notice/{id}")
    public ResponseEntity<Notice> noticeIdFind(@PathVariable Long id){
        return new ResponseEntity<>(noticeService.noticeIdFind(id),HttpStatus.OK);
    }
    // 유저- 공지사항(제목) 검색 조회
    @GetMapping("/notice/title/search/{title}")
    public ResponseEntity<List<Notice>> noticeTitleFind(@PathVariable String title){
        return new ResponseEntity<>(noticeService.noticeTitleFind(title),HttpStatus.OK);
    }

    // 관리자- 로그인 중 공지사항 추가
    @PostMapping("/admin/notice/add")
    public ResponseEntity<Notice> noticeAdd(@RequestBody Notice notice, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new UnauthorizedUserException("로그인 후 이용 가능합니다.");
        }
        String username = (String) session.getAttribute("username");
        return new ResponseEntity<>(noticeService.noticeAdd(notice,username),HttpStatus.CREATED);
    }

    //관리자- 로그인 중 공지사항 삭제
    @DeleteMapping("/admin/notice/delete/{id}")
    public ResponseEntity<Notice> noticeDelete(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new UnauthorizedUserException("로그인한 사용자만 삭제가 가능합니다.");
        }
        String username = (String) session.getAttribute("username");
        return new ResponseEntity<>(noticeService.noticeDelete(id, username), HttpStatus.OK);
    }

    // 관리자- 로그인 중 공지사항 수정
    @PutMapping("/admin/notice/update/{id}")
    public ResponseEntity<Notice> noticeUpdate(@PathVariable Long id, @RequestBody Notice notice, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new UnauthorizedUserException("로그인한 사용자만 수정이 가능합니다.");
        }
        String username = (String) session.getAttribute("username");
        return new ResponseEntity<>(noticeService.noticeUpdate(id, notice, username), HttpStatus.OK);
    }
}
