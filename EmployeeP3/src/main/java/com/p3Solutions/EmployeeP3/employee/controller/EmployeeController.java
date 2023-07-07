package com.p3Solutions.EmployeeP3.employee.controller;

import java.util.List;

import com.p3Solutions.EmployeeP3.employee.dto.EmployeeRequestDto;
import com.p3Solutions.EmployeeP3.employee.dto.EmplyoeeResponseDto;
import com.p3Solutions.EmployeeP3.payslip.dto.PaySlipsResponceDto;
import com.p3Solutions.EmployeeP3.payslip.entity.PaySlip;
import com.p3Solutions.EmployeeP3.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/emp")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public String savEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        return employeeService.saveEmployee(employeeRequestDto);
    }

    @GetMapping("/{id}")
    public EmplyoeeResponseDto getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("{id}")
    public String deleteEmployeeById(@PathVariable int id) {
        return employeeService.deleteEmployeeById(id);
    }

    @PutMapping("/{id}")
    public String updateEmployeeById(@RequestBody EmployeeRequestDto employeeRequestDto, @PathVariable int id) {
        return employeeService.updateEmployeeById(employeeRequestDto, id);
    }

    @GetMapping
    public List<EmplyoeeResponseDto> getALL(){
        return employeeService.getAll();
    }

    @PostMapping("addNewPaySlip")
    public String updateNewPayslipByEmpId(@RequestBody PaySlip paySlip, @RequestParam int id) {
        return employeeService.addNewPaySlipUsingEmpId(id, paySlip);

    }

    @GetMapping("paySlips")
    public List<PaySlipsResponceDto> getEmpPaySlips(@RequestParam int id) {
        return employeeService.getEmpPaySlips(id);
    }

    @GetMapping("/dept/{id}")
    public List<EmplyoeeResponseDto> getAllEmpInDeptID(@PathVariable int id){
        return employeeService.getAllEmpInDeptId(id);
    }
    @PostMapping("/csv")
    public String csvUpLoad(@RequestParam("file")MultipartFile multipartFile){
        employeeService.csvFileRaeder(multipartFile);
        return "jg";

    }
}
