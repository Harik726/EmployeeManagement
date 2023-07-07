package com.p3Solutions.EmployeeP3.payslip.dto;

import lombok.Data;

import java.time.YearMonth;
@Data
public class PaySlipsResponceDto {
    private int payShilpId;
    private double totalSalary;
    private YearMonth yearMonth;
}
