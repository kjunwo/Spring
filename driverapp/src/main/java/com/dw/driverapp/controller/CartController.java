package com.dw.driverapp.controller;

import com.dw.driverapp.dto.CartDTO;
import com.dw.driverapp.exception.ResourceNotFoundException;
import com.dw.driverapp.exception.UnauthorizedUserException;
import com.dw.driverapp.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    CartService cartService;

    // 유저 -> 모든 장바구니 목록 조회
    @GetMapping("/cart/all")
    public ResponseEntity<List<CartDTO>> getAllCart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new ResourceNotFoundException("로그인한 사용자만 장바구니 조회가 가능합니다.");
        }
        return new ResponseEntity<>(cartService.getAllCart(), HttpStatus.OK);
    }

    // 유저 -> 특정 유저 장바구니 조회
    @GetMapping("/cart/user/{username}")
    private ResponseEntity<List<CartDTO>> findUserName(@PathVariable String username, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new ResourceNotFoundException("로그인한 사용자만 장바구니 조회가 가능합니다.");
        }
        return new ResponseEntity<>(cartService.findUserName(username), HttpStatus.OK);
    }

    // 유저 - 로그인 중인 사용자의 이름으로 장바구니 추가
    @PostMapping("/cart/user/add")
    public ResponseEntity<CartDTO> addSubjectToCart(@RequestParam String username, @RequestParam Long subjectId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new ResourceNotFoundException("로그인한 사용자만 장바구니에 과목을 추가할 수 있습니다.");
        }
        CartDTO cartDTO = cartService.addSubjectToCart(username, subjectId);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    // 유저 -> 로그인한 회원이 과목 id로 장바구니 삭제
    @DeleteMapping("/cart/user/delete/subject/{subjectId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long subjectId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            throw new ResourceNotFoundException("로그인한 사용자만 장바구니에 과목을 삭제할 수 있습니다.");
        }
        String username = (String) session.getAttribute("username");
        cartService.deleteCart(subjectId, username);
        return new ResponseEntity<>("삭제가 완료 되었습니다.", HttpStatus.OK);
    }

    // 유저- 로그인한 회원의 장바구니에서 과목아이디로 구매(자동으로 유저의 포인트에서 과목의 가격을 계산)
    @PostMapping("/cart/enrollment/{id}")
    public ResponseEntity<String> cartEnrollment(@PathVariable Long id, HttpServletRequest request) {
        String loggedInUsername = (String) request.getSession().getAttribute("username");
        if (loggedInUsername == null) {
            throw new UnauthorizedUserException("로그인한 사용자만 장바구니를 구매할 수 있습니다.");
        }
        cartService.cartEnrollment(loggedInUsername, id);
        return new ResponseEntity<>("장바구니 구매가 완료되었습니다.", HttpStatus.OK);
    }
}
