package com.dw.jdbcapp.repository.iface;

import com.dw.jdbcapp.model.Department;

import java.util.List;

public interface DepartmentRepository {
    List<Department> getAllDepartments();
}
