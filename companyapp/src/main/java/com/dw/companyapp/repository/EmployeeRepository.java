package com.dw.companyapp.repository;

import com.dw.companyapp.dto.EmployeeDepartmentDTO;
import com.dw.companyapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("select e.hireDate, d.departmentName, e.name " +
            "from Employee e join e.department d")
    List<Object[]> getEmployeesWithDepartName();
    @Query ("SELECT new com.dw.companyapp.dto.EmployeeDepartmentDTO(e.name, e.hireDate, d.name) " +
                  "FROM Employee e JOIN e.department d")
    List<EmployeeDepartmentDTO> getEmployeesWithDepartName2();
}
