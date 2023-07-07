package com.p3Solutions.EmployeeP3.employee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Salary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int salId;
	@Column(nullable = false)
	private double basicSal;
	private double pf;
	private double profesionalTax;
}
