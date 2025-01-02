package com.dw.companyapp.service;

import com.dw.companyapp.model.Department;
import com.dw.companyapp.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> saveDepartmentList(
                           List<Department> departmentList) {
        return departmentRepository.saveAll(departmentList);
    }

    public Department updateDepartment(Department department) {
        return departmentRepository.findAllById(department.getDepartmentId())

    }

    public String deleteDepartment(String id) {
        return departmentRepository.findAllById();
    }
}








