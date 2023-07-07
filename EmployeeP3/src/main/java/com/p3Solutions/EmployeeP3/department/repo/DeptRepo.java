package com.p3Solutions.EmployeeP3.department.repo;

import com.p3Solutions.EmployeeP3.department.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepo extends JpaRepository<Dept,Integer> {

}
