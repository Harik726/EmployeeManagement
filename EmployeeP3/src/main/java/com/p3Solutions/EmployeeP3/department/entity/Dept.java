package com.p3Solutions.EmployeeP3.department.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p3Solutions.EmployeeP3.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deptId;
    private String deptName;
    private String coordinator;
    @JsonIgnore
    @OneToMany(mappedBy = "dept",cascade = CascadeType.ALL)
    private List<Employee> employees;

}
