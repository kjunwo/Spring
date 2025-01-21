package com.dw.driverapp.service;

import com.dw.driverapp.dto.SubjectDTO;
import com.dw.driverapp.exception.ResourceNotFoundException;
import com.dw.driverapp.exception.UnauthorizedActionException;
import com.dw.driverapp.model.Subject;
import com.dw.driverapp.model.User;
import com.dw.driverapp.repository.SubjectRepository;
import com.dw.driverapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    UserRepository userRepository;

    // 유저- 과목 전체를 조회
    public List<SubjectDTO> getAllSubject(){
        return subjectRepository.findAll().stream().map(Subject::toDTO)
                .collect(Collectors.toList());
    }

    // 유저- 과목을 id로 조회
    public SubjectDTO subjectIdfind(Long id){
        Subject subject = subjectRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("입력하신 정보의 과목이 존재하지 않습니다."));
        return subject.toDTO();
    }
    public SubjectDTO subjectAdd(SubjectDTO subjectDTO, String username){
        User instructor = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("사용자가 존재하지 않습니다."));
        Subject subject = new Subject();
        subject.setTitle(subjectDTO.getTitle());
        subject.setExplanation(subjectDTO.getExplanation());
        subject.setPrice(subjectDTO.getPrice());
        subject.setUser_fk(instructor);
        Subject savedSubject = subjectRepository.save(subject);
        return savedSubject.toDTO();
    }

    //강사- 강의 삭제
    public void deleteSubject(Long subjectId, String instructorUsername) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 강의입니다."));
        if (!subject.getUser_fk().getUserName().equals(instructorUsername)) {
            throw new UnauthorizedActionException("본인이 생성한 강의만 삭제할 수 있습니다.");
        }
        subjectRepository.delete(subject);
    }

    // 수정하려는 강의를 찾기
    public SubjectDTO updateSubject(Long id, String username, SubjectDTO subjectDTO) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 강의입니다."));
        if (!subject.getUser_fk().getUserName().equals(username)) {
            throw new UnauthorizedActionException("본인이 생성한 강의만 수정할 수 있습니다.");
        }
        subject.setTitle(subjectDTO.getTitle());
        subject.setExplanation(subjectDTO.getExplanation());
        subject.setPrice(subjectDTO.getPrice());
        subjectRepository.save(subject);
        return subject.toDTO();
    }

}
