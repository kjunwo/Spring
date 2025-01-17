package com.dw.driverapp.controller;

import com.dw.driverapp.dto.UserDTO;
import com.dw.driverapp.exception.UnauthorizedUserException;
import com.dw.driverapp.model.User;
import com.dw.driverapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    // 유저 - 회원가입
    @PostMapping("/user/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

    // 관리자 - 모든 회원정보 조회
    @GetMapping("/user/me")
    public ResponseEntity<List<User>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    // 유저 - 로그인
    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO,
                                        HttpServletRequest request) {
        String username = userDTO.getUserName();
        String password = userDTO.getPassword();

        if (userService.validateUser(username, password)) {

            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            request.getSession().setAttribute("username", username);

            return new ResponseEntity<>(
                    "로그인이 완료되었습니다.",
                    HttpStatus.OK);
        } else {
            throw new UnauthorizedUserException("입력하신 정보가 올바르지 않습니다.");
        }
    }

    // 유저 - 로그아웃
    @PostMapping("/user/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return new ResponseEntity<>(
                "로그아웃 되었습니다.",
                HttpStatus.OK);
    }

    // 유저 - username으로 정보 조회
    @GetMapping("/user/{user_name}")
    public ResponseEntity<User> getUserName(@PathVariable String user_name){
        return new ResponseEntity<>(userService.getUserName(user_name), HttpStatus.OK);
    }

    // 유저 - email로 정보 조회
    @GetMapping("/user/email/{email}")
    public ResponseEntity<User> getUserEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserEmail(email), HttpStatus.OK);
    }

    // 유저 - realname으로 정보 조회
    @GetMapping("/user/realname/{real_name}")
    public ResponseEntity<List<User>> getUserRealName(@PathVariable String real_name){
        return new ResponseEntity<>(userService.getUserRealName(real_name), HttpStatus.OK);
    }

    // 유저 - birthdate로 정보 조회
    @GetMapping("/user/birthdate/{birthdate}")
    public ResponseEntity<List<User>> userBirthdateFind(@PathVariable LocalDate birthdate) {
        return new ResponseEntity<>(userService.userBirthdateFind(birthdate), HttpStatus.OK);
    }

    // 관리자 - 권한으로 정보 조회*****
    @GetMapping("/user/authority/{authority}")
    public ResponseEntity<List<User>> userauthorityFind(@PathVariable String authority){
        return new ResponseEntity<>(userService.userauthorityFind(authority),HttpStatus.OK);
    }

    // 유저 - 지정된 날짜 이후 가입자 정보 조회
    @GetMapping("/user/over/{date}")
    public ResponseEntity<List<User>> userdateoverFind(@PathVariable LocalDate date){
        return new ResponseEntity<>(userService.userdateoverFind(date),HttpStatus.OK);
    }

    // 유저 - 지정된 날짜 이전 가입자 정보 조회
    @GetMapping("/user/under/{date}")
    public ResponseEntity<List<User>> userdateunderFind(@PathVariable LocalDate date){
        return new ResponseEntity<>(userService.userdateunderFind(date),HttpStatus.OK);
    }

    // 유저 - 지정된 날짜 가입자 정보 조회
    @GetMapping("/user/date/{date}")
    public ResponseEntity<List<User>> userdateFind(@PathVariable LocalDate date){
        return new ResponseEntity<>(userService.userdateFind(date),HttpStatus.OK);
    }

    // 유저 - 지정된 날짜 사이에 가입한 정보 조회
    @GetMapping("/user/{date1}/{date2}")
    public ResponseEntity<List<User>> userbetweenFind(@PathVariable LocalDate date1,@PathVariable LocalDate date2){
        return new ResponseEntity<>(userService.userbetweenFind(date1,date2),HttpStatus.OK);
    }

    // 유저 - 패스워드 변경
    @PutMapping("/user/update/password/{id}")
    public ResponseEntity<User> updateUserPassword(@PathVariable String id, @RequestBody User user) {
        return new ResponseEntity<>(userService.updateUserPassword(id,user), HttpStatus.OK);
    }

    // 유저 - 회원탈퇴
    @DeleteMapping("/user/me/delete/{user_name}")
    public ResponseEntity<String> deleteUser(@RequestParam String id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }
}



