package com.dw.driverapp.controller;


import com.dw.driverapp.dto.EnrollmentDTO;
import com.dw.driverapp.dto.SubjectEnrollmentDTO;
import com.dw.driverapp.exception.ResourceNotFoundException;
import com.dw.driverapp.exception.UnauthorizedUserException;
import com.dw.driverapp.service.EnrollmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EnrollmentController {
    @Autowired
    EnrollmentService enrollmentService;


    // 관리자 -> 로그인 한 사람이 관리자일 경우 모든 수강신청 내역 조회
    @GetMapping("/enrollment/all")
    private ResponseEntity<List<EnrollmentDTO>> getAllEnrollment(HttpServletRequest request){
        // 세션에서 로그인한 사용자 정보 가져오기
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new UnauthorizedUserException("로그인한 사용자만 수강 신청 조회가 가능합니다.");
        }
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            throw new UnauthorizedUserException("관리자만 모든 수강 신청을 조회할 수 있습니다.");
        }
        List<EnrollmentDTO> enrollmentList = enrollmentService.getAllEnrollment();
        return new ResponseEntity<>(enrollmentList, HttpStatus.OK);
    }

    // 유저 -> 로그인한 본인이 맞을 경우 과목 ID로 수강신청 내역 조회
    @GetMapping("/enrollment/subject/{id}")
    public ResponseEntity<List<EnrollmentDTO>> getSubjectId(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new UnauthorizedUserException("로그인한 사용자만 수강 신청 내역을 조회할 수 있습니다.");
        }
        String username = (String) session.getAttribute("username");
        return new ResponseEntity<>(enrollmentService.getSubjectId(id, username), HttpStatus.OK);
    }

    // 관리자- 유저네임으로 수강 신청을 조회
    @GetMapping("/enrollment/{username}")
    public ResponseEntity<List<EnrollmentDTO>> enrollmentFindUsername(@PathVariable String username, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null || session.getAttribute("role") == null) {
            throw new UnauthorizedUserException("로그인한 사용자만 수강 신청 내역을 조회할 수 있습니다.");
        }
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            throw new UnauthorizedUserException("관리자만 다른 사용자의 수강 신청 내역을 조회할 수 있습니다.");
        }
        return new ResponseEntity<>(enrollmentService.enrollmentFindUsername(username), HttpStatus.OK);
    }


    //유저- 로그인한 회원의 수강신청을 조회
    @GetMapping("/enrollment/login")
    public ResponseEntity<List<EnrollmentDTO>> enrollmentFindLoginUsername(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new ResourceNotFoundException("로그인한 사용자만 조회가 가능합니다.");
        }
        String username = (String) session.getAttribute("username");
        return new ResponseEntity<>(enrollmentService.enrollmentFindLoginUsername(username), HttpStatus.OK);
    }
    // 유저- 로그인한 회원의 수강완료를 저장하는 기능
    @PostMapping("/enrollment/complete/{subjectId}")
    public ResponseEntity<String> completeSubject(
            @PathVariable Long subjectId,
            HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            return new ResponseEntity<>("로그인한 사용자만 수강 완료를 처리할 수 있습니다.", HttpStatus.UNAUTHORIZED);
        }
        try {
            enrollmentService.completeSubject(username, subjectId);
            return new ResponseEntity<>("해당 과목을 수강 완료 했습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // 유저- 로그인한 회원의 과목의 수강완료 여부를 조회하는 기능(관리자나 강사 일 경우 모든 회원의 정보를 조회)
    @GetMapping("/enrollment/subject/completed")
    public ResponseEntity<List<SubjectEnrollmentDTO>> enrollmentCompleted (HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new ResourceNotFoundException("로그인한 사용자만 조회가 가능합니다.");
        }
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        List<SubjectEnrollmentDTO> enrollmentDTOs;
        if ("ADMIN".equals(role) || "INSTRUCTOR".equals(role)) {

            enrollmentDTOs = enrollmentService.getAllEnrollments();
        } else {
            enrollmentDTOs = enrollmentService.enrollmentCompleted(username);
        }
        return new ResponseEntity<>(enrollmentDTOs, HttpStatus.OK);
    }
}
