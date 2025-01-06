package com.dw.basicapp.controller;

import com.dw.basicapp.model.User;
import com.dw.basicapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return new ResponseEntity<>(
                userService.saveUser(user),
                HttpStatus.OK);
    }
    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(
                userService.getAllUsers(),
                HttpStatus.OK);
    }
}
