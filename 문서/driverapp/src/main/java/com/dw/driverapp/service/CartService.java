package com.dw.driverapp.service;

import com.dw.driverapp.dto.CartDTO;
import com.dw.driverapp.exception.ResourceNotFoundException;
import com.dw.driverapp.exception.UnauthorizedUserException;
import com.dw.driverapp.model.Cart;
import com.dw.driverapp.model.Subject;
import com.dw.driverapp.model.User;
import com.dw.driverapp.repository.CartRepository;
import com.dw.driverapp.repository.SubjectRepository;
import com.dw.driverapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SubjectRepository subjectRepository;

    // 유저 -> 모든 장바구니 목록 조회
    public List<CartDTO> getAllCart() {
        return cartRepository.findAll().stream().map(Cart::ToDto).toList();
    }

    // 유저 -> 특정 유저 장부구니 조회
    public List<CartDTO> findUserName(String username) {
        return cartRepository.findByUserUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("없음"))
                .stream()
                .map(Cart::ToDto)
                .toList();
    }
    public CartDTO addSubjectToCart(String username, Long subjectId) {
        User user = userRepository.findById(username).orElseThrow(() -> new ResourceNotFoundException("해당 유저가 존재하지 않습니다."));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new ResourceNotFoundException("해당 과목이 존재하지 않습니다."));
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setSubject(subject);
        Cart savedCart = cartRepository.save(cart);
        return savedCart.ToDto();
    }

    // 유저 -> 과목 id로 장바구니 삭제
    public List<CartDTO> deleteCart(Long subjectId, String username) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 과목입니다."));

        Cart cart = cartRepository.findByUserUserNameAndSubjectId(username, subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 과목을 장바구니에 추가한 사용자가 아닙니다."));

        if (!cart.getUser().getUserName().equals(username)) {
            throw new UnauthorizedUserException("사용자가 아니므로 해당 항목을 삭제할 수 없습니다.");
        }

        cartRepository.delete(cart);
        return cartRepository.findByUserUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("사용자의 장바구니가 없습니다."))
                .stream()
                .map(Cart::ToDto)
                .collect(Collectors.toList());

    }
}
