package com.p3Solutions.EmployeeP3.employee.dto;

import com.p3Solutions.EmployeeP3.payslip.dto.PaySlipsResponceDto;
import lombok.Data;

import java.util.List;
@Data
public class EmplyoeeResponseDto {
    private int empId;
    private String email;
    private String area;
    private String city;
    private String  pin;
    private double basicSal;
    private List<PaySlipsResponceDto> paySlips;
    private int deptId;
}
