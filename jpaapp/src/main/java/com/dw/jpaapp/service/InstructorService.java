package com.dw.jpaapp.service;

import com.dw.jpaapp.dto.InstructorDTO;
import com.dw.jpaapp.dto.InstructorGithubDTO;
import com.dw.jpaapp.model.Instructor;
import com.dw.jpaapp.model.InstructorProfile;
import com.dw.jpaapp.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {
    @Autowired
    InstructorRepository instructorRepository;

    public List<InstructorDTO> getAllInstructors() {
        return instructorRepository.findAll().stream().map(Instructor::toDTO).
                collect(Collectors.toList());
    }
    public InstructorDTO getInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow();
        return instructor.toDTO();
    }
    // 과제5-4. Instructor 정보를 새로 저장
    public InstructorDTO saveInstructor(InstructorDTO instructorDTO) {
        Instructor instructor = new Instructor();
        instructor.setName(instructorDTO.getName());
        instructor.setCareer(instructorDTO.getCareer());
        instructor.setCourseList(instructorDTO.getCourseIds().stream()
                .map(id->courseRepository.findById(id))
                .map(optional->optional.orElseThrow(()->new RuntimeException("No course")))
                .peek(course->course.setInstructor_fk(instructor))
                .toList()
        );
        return instructorRepository.save(instructor).toDTO();
    }
    // 과제5-5. 전체 강사의 강사ID, 강사이름, github url을 조회
    public List<Object[]> getInstructorGithub() {
        List<Object[]> objects = new ArrayList<>();
        for (InstructorProfile data : instructorProfileRepository.findAll()) {
            Object[] temp = new Object[3];
            temp[0] = data.getInstructor().getId();
            temp[1] = data.getInstructor().getName();
            temp[2] = data.getGithubUrl();
            objects.add(temp);
        }
        return objects;
    }
    // DTO를 이용하는 방법
    public List<InstructorGithubDTO> getInstructorGithub2() {
        return instructorProfileRepository.getInstructorGithub();
    }
}