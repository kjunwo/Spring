package com.dw.companyapp.repository;

import com.dw.companyapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
@Query("select c from Customer c where c.mileage > (select avg(c2.mileage) from Customer c2)")
List<Customer> getCustomersWithHighMileThanAvg();
@Query("select c from Customer c join MileageGrade mg on c.mileage between mg.lowerMileage and mg.upperMileage where gradeName = :grade")
List<Customer> getCustomersByMileageGrade(String grade);
}
