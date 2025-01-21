package com.dw.driverapp.service;

import com.dw.driverapp.dto.UserDTO;
import com.dw.driverapp.dto.UserPointDTO;
import com.dw.driverapp.exception.InvalidRequestException;
import com.dw.driverapp.exception.ResourceNotFoundException;
import com.dw.driverapp.model.Enrollment;
import com.dw.driverapp.model.User;
import com.dw.driverapp.repository.AuthorityRepository;
import com.dw.driverapp.repository.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    AuthorityRepository authorityRepository;

    // 유저- 회원가입
    public UserDTO registerUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findById(userDTO.getUserName());
        if (user.isPresent()) {
            throw new InvalidRequestException("입력하신 정보가 이미 존재합니다.");
        }
        return userRepository.save(
                new User(
                        userDTO.getUserName(),
                        passwordEncoder.encode(userDTO.getPassword()),
                        userDTO.getEmail(),
                        userDTO.getRealName(),
                        userDTO.getBirthdate(),
                        authorityRepository.findById("User")
                                .orElseThrow(() -> new ResourceNotFoundException("권한 없음")),
                        LocalDate.now(),
                        10000)
        ).toDTO();// 회원가입
    }

    // 관리자 - 모든 회원정보 조회
    public List<User> getAllUser() {
        return userRepository.findAll(); // 회원정보 조회
    }

    // 유저 - 로그인, 로그아웃
    public boolean validateUser(String username, String password) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new InvalidRequestException("사용자의 이름이 잘못되었습니다."));
        return passwordEncoder.matches(password, user.getPassword());
    }

    // 관리자, 유저- username으로 정보 조회
    public User getUserName(String user_name) {
        return userRepository.findById(user_name)
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 회원이 존재하지 않습니다."));
    }

    // 관리자, 유저- email으로 정보 조회
    public User getUserEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 email을 가진 회원이 존재하지 않습니다."));
    }

    // 관리자, 유저- realname으로 정보 조회
    public List<User> getUserRealName(String real_name) {
        return userRepository.findByRealName(real_name)
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 회원이 존재하지 않습니다."));
    }

    // 유저 - 생일로 정보 조회
    public List<User> userBirthdateFind(LocalDate birthdate) {
        return userRepository.findByBirthdate(birthdate)
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 생일의 정보를 가진 회원이 존재하지 않습니다."));
    }

    // 관리자- 권한으로 정보 조회******
    public List<User> userauthorityFind(String authority) {
        return userRepository.findByAuthority_AuthorityName(authority)
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 권한이 존재하지 않습니다."));
    }

    // 유저- 지정된 날짜 이후 가입자 정보 조회
    public List<User> userdateoverFind(LocalDate date) {
        return userRepository.createdAtoverdate(date)
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 날짜 이후에 가입한 회원이 없습니다."));
    }

    // 유저- 지정된 날짜 이전 가입자 정보 조회
    public List<User> userdateunderFind(LocalDate date) {
        return userRepository.createdAtunderdate(date)
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 날짜 이전에 가입한 회원이 없습니다."));
    }

    // 유저- 지정된 날짜 가입자 정보 조회
    public List<User> userdateFind(LocalDate date) {
        return userRepository.findBycreatedAt(date)
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 날짜에 가입한 회원이 없습니다."));
    }

    //유저- 지정된 날짜 사이에 가입한 정보 조회
    public List<User> userbetweenFind(LocalDate date1, LocalDate date2) {
        return userRepository.createdAtbetweendate(date1, date2)
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 날짜에 사이에 가입한 회원이 없습니다."));
    }

    // 유저 - 패스워드 변경
    public User userUpdatePassWord(User user) {
        User user1 = userRepository.findById(user.getUserName()).orElseThrow(() -> new ResourceNotFoundException("없음"));
        user1.setPassword(user.getPassword());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        return userRepository.save(user1);
    }

    // 유저 - 회원탈퇴
    public void deleteUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("해당 유저를 찾을 수 없습니다."));

        userRepository.delete(user);
    }

    // 유저- 가장 먼저 가입한 유저 조회
    public List<User> firstUser() {
        return userRepository.findFirstCreatedAt()
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 정보가 없습니다."));
    }

    // 유저- 가장 최근 가입한 유저 조회
    public List<User> lastUser() {
        return userRepository.findLastCreatedAt()
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("입력하신 정보가 없습니다."));
    }

    // 유저 - 포인트 내역 조회
    public User userPoint(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("포인트가 없습니다."));
    }

//    // 유저 - 포인트 사용
//    public User userPointUse(String username, User user) {
//        return userRepository.findByUserName(username)
//                .filter(existingUser -> existingUser.getPoints() >= user.getPoints())
//                .map(existingUser -> {
//                    int newPoints = existingUser.getPoints() - user.getPoints();
//                    userRepository.updateUserPoint(username, newPoints);
//                    existingUser.setPoints(newPoints);
//                    return userRepository.save(existingUser);
//                })
//                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없거나 포인트가 부족합니다."));
//    }



    // 관리자- 포인트가 가장 많은 회원 조회
    public List<User> userPointMost(){
        return userRepository.MostPointUser()
                .filter(users -> !users.isEmpty())
                .orElseThrow(()-> new ResourceNotFoundException("정보를 찾을 수 없습니다."));
    }

    //관리자- 포인트가 가장 적은 회원 조회
    public List<User> userPointLeast(){
        return userRepository.leastPointUser()
                .filter(users -> !users.isEmpty())
                .orElseThrow(()-> new ResourceNotFoundException("정보를 찾을 수 없습니다."));
    }

    // 관리자- 회원들이 평균 포인트 조회
    public Double userPointAverage(){
        return userRepository.findAveragePoint()
                .orElseThrow(()-> new ResourceNotFoundException("정보를 불러올 수 없습니다."));
    }

    // 관리자- 모든 회원들의 포인트 조회
    public List<UserPointDTO> userAllPoint(){
        return userRepository.findAll().stream()
                .map(User::todto)
                .collect(Collectors.toList());
    }
}





