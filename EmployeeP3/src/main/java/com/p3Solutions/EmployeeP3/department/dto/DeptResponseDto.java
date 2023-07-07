package com.p3Solutions.EmployeeP3.department.dto;

import com.p3Solutions.EmployeeP3.employee.dto.EmplyoeeResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class DeptResponseDto {
    private int  deptId;
    private String deptName;
    private String coordinator;
}
