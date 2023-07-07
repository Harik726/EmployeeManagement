package com.p3Solutions.EmployeeP3.employee.dto;

import com.p3Solutions.EmployeeP3.employee.entity.Address;
import com.p3Solutions.EmployeeP3.department.entity.Dept;
import com.p3Solutions.EmployeeP3.payslip.entity.PaySlip;
import com.p3Solutions.EmployeeP3.employee.entity.Salary;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeRequestDto {
    private String name;
    private String email;
    private String ph;
    private Address address;
    private Salary salary;
//    private List<PaySlip> paySlips;
    private int deptid;

}
