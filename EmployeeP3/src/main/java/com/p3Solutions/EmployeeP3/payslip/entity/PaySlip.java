package com.p3Solutions.EmployeeP3.payslip.entity;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p3Solutions.EmployeeP3.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaySlip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int payShilpId;
	private double totalAllowance;
	private double grossPay;
	private double netPay;
	private double totalSalary;
	private YearMonth yearMonth;
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_emp_id")
	private Employee employee;

}
