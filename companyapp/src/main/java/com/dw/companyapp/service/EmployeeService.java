package com.dw.companyapp.service;

import com.dw.companyapp.dto.EmployeeDepartmentDTO;
import com.dw.companyapp.exception.ResourceNotFoundException;
import com.dw.companyapp.model.Employee;
import com.dw.companyapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // 과제 3-1 사원정보를 조회할때 사원번호가 올바르지 않은 경우의 예외 처리
    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 사원을 찾을 수 없습니다."));
    }

    public List<Map<String,Object>> getEmployeesWithDepartName() {
        List<Object[]> objects = employeeRepository.getEmployeesWithDepartName();
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Object[] data : objects) {
            Map<String, Object> employeeMap = new HashMap<>();
            Employee employee = (Employee) data[0];
            String departmentName = (String) data[1];
            employeeMap.put("이름", employee.getName());
            employeeMap.put("입사일", employee.getHireDate());
            employeeMap.put("부서명", departmentName);
            maps.add(employeeMap);
        }
        return maps;
    }

    public List<EmployeeDepartmentDTO> getEmployeesWithDepartName2() {
        return employeeRepository.getEmployeesWithDepartName2();
    }

    // 과제 1-3 부서번호와 직위를 기준으로 해당 부서에 근무하는 특정 직위의 사원 정보를 조회하는 API
    // 과제 3-3 부서번호와 직위로 사원정보를 조회할때 데이터가 없는 경우의 예외처리
    public List<Employee> getEmployeesWithDepartmentAndPosition(
            String departmentNumber, String position
    ) {
        return null;
    }

    // 과제 2-3 사원테이블에 사원 1명을 새로 추가하는 API
    public Employee saveEmployee(Employee employee) {
        return null;
    }

    // 과제 4-3 입사일을 매개변수로 해당 입사일 이후로 입사한 사원들을 조회하는 API
    // hiredate를 0으로 입력하면 가장 최근 입사한 사원의 정보를 조회하시오.
    public List<Employee> getEmployeesByHiredate(String hiredate) {
        return null;
    }
}








