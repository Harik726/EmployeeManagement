package com.p3Solutions.EmployeeP3.employee.repo;

import com.p3Solutions.EmployeeP3.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

}
