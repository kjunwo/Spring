package com.dw.jdbcapp.controller;

import com.dw.jdbcapp.model.Department;
import com.dw.jdbcapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/find-all-departments")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
    // single data (저장할 데이터 객체 1개 처리용)
    @PostMapping("/post/department")
    public Department saveDepartment(@RequestBody Department department) {
        return departmentService.saveDepartment(department);
    }

    // multiple data (저장할 데이터가 리스트임)
    @PostMapping("/post/departmentlist")
    public List<Department> saveDepartmentList
        (@RequestBody List<Department> departmentList) {
        return departmentService.saveDepartmentList(departmentList);
    }

    @PutMapping("/put/department")
    public Department updateDepartment(@RequestBody Department department){
        return departmentService.updateDepartment(department);
    }

    @DeleteMapping("/delete/department/id/{id}")
    public String deletedepartment(@PathVariable String id) {
        return "부서번호 : " + departmentService.deleteDepartment(id) + " 가 삭제되었습나다.";
    }
}
