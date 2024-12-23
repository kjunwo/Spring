package com.dw.jdbcapp.repository.iface;

import com.dw.jdbcapp.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository {
    List<Employee> getAllEmployees();
    public Employee getEmployeeById(String id);
    public List<Map<String, Object>> getEmployeesWithDepartName();
    public List<Employee> getEmployeeByNumber(String number,String position);
    public Employee saveemployee(Employee employee);
    List<Employee> getEmployeeByDate(String date);
    List<Employee> getEmployeeByHiredate(String hiredate);
    List<Employee> getEmployeeByHiredate1();
}
