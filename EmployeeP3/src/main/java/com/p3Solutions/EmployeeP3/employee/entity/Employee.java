package com.p3Solutions.EmployeeP3.employee.entity;

import java.util.List;

import com.p3Solutions.EmployeeP3.department.entity.Dept;
import com.p3Solutions.EmployeeP3.payslip.entity.PaySlip;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int empId;
	private String name;
	@Column(unique = true)
	private String email;
	@Column(length = 10)
	private String ph;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Salary salary;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "employee")
	private List<PaySlip> paySlips;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "dept_dept_id")
	private Dept dept;
}
