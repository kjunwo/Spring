package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.model.Department;
import com.dw.jdbcapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerDepartment {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/find-all-departments")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
}
