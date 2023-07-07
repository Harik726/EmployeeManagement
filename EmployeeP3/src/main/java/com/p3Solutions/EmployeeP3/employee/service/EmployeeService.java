package com.p3Solutions.EmployeeP3.employee.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.opencsv.CSVReader;
import com.p3Solutions.EmployeeP3.department.entity.Dept;
import com.p3Solutions.EmployeeP3.department.repo.DeptRepo;
import com.p3Solutions.EmployeeP3.employee.dto.EmployeeRequestDto;
import com.p3Solutions.EmployeeP3.employee.dto.EmplyoeeResponseDto;
import com.p3Solutions.EmployeeP3.payslip.dto.PaySlipsResponceDto;
import com.p3Solutions.EmployeeP3.employee.entity.Address;
import com.p3Solutions.EmployeeP3.employee.entity.Employee;
import com.p3Solutions.EmployeeP3.payslip.entity.PaySlip;
import com.p3Solutions.EmployeeP3.employee.entity.Salary;
import com.p3Solutions.EmployeeP3.employee.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private DeptRepo deptRepo;

    public String saveEmployee(EmployeeRequestDto employeeRequestDto) {
        Employee employee = new Employee();
        employee.setName(employeeRequestDto.getName());
        employee.setPh(employeeRequestDto.getPh());
        employee.setEmail(employeeRequestDto.getEmail());

        Address address = new Address();
        address.setPin(employeeRequestDto.getAddress().getPin());
        address.setArea(employeeRequestDto.getAddress().getArea());
        address.setCity(employeeRequestDto.getAddress().getCity());

        employee.setAddress(address);


        Salary salary = employeeRequestDto.getSalary();
//		PF
        salary.setPf((int) (salary.getBasicSal() * 0.12));

//		Professional TAx
        if (salary.getBasicSal() >= 15000) {
            salary.setProfesionalTax(200);
        } else if (salary.getBasicSal() >= 10000) {
            salary.setProfesionalTax(150);
        } else {
            salary.setProfesionalTax(0);
        }
        employee.setSalary(salary);

//		Pay Slips
//        List<PaySlip> paySlips = employeeRequestDto.getPaySlips();
//
//        for (PaySlip paySlip : paySlips) {
//            paySlip.setGrossPay(salary.getBasicSal() + paySlip.getTotalAllowance());
//            paySlip.setNetPay(paySlip.getGrossPay() - salary.getPf() - salary.getProfesionalTax());
//            paySlip.setTotalSalary(
//                    salary.getBasicSal() + paySlip.getTotalAllowance() + salary.getPf() + salary.getProfesionalTax());
//			paySlip.setEmployee(employee);
//
//        }
//        employee.setPaySlips(paySlips);

        employee.setDept(deptRepo.findById(employeeRequestDto.getDeptid()).get());


        employeeRepo.save(employee);
        return "Employee Saved successfully";
    }


    public EmplyoeeResponseDto getEmployeeById(int id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id: " + id));
        EmplyoeeResponseDto emplyoeeResponseDto = new EmplyoeeResponseDto();
        emplyoeeResponseDto.setEmpId(employee.getEmpId());
        emplyoeeResponseDto.setEmail(employee.getEmail());
        emplyoeeResponseDto.setArea(employee.getAddress().getArea());
        emplyoeeResponseDto.setCity(employee.getAddress().getCity());
        emplyoeeResponseDto.setPin(employee.getAddress().getPin());
        emplyoeeResponseDto.setBasicSal(employee.getSalary().getBasicSal());
        List<PaySlip> paySlips = employee.getPaySlips();
        List<PaySlipsResponceDto> paySlipsResponceDtos = paySlips.stream()
                .map(this::convertEntityResponse)
                .collect(Collectors.toList());
        emplyoeeResponseDto.setPaySlips(paySlipsResponceDtos);
        return emplyoeeResponseDto;
    }

    public PaySlipsResponceDto convertEntityResponse(PaySlip paySlip) {
        PaySlipsResponceDto paySlipsResponceDto = new PaySlipsResponceDto();
        paySlipsResponceDto.setPayShilpId(paySlip.getPayShilpId());
        paySlipsResponceDto.setTotalSalary(paySlip.getTotalSalary());
        paySlipsResponceDto.setYearMonth(paySlip.getYearMonth());
        return paySlipsResponceDto;
    }


    public String deleteEmployeeById(int id) {
        employeeRepo.deleteById(id);
        return "deleted";
    }


    public String updateEmployeeById(EmployeeRequestDto update, int id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));

        employee.setName(update.getName());
        employee.setEmail(update.getEmail());
        employee.setPh(update.getPh());

        Address address = employee.getAddress();
        Address updateAddress = update.getAddress();
        address.setArea(updateAddress.getArea());
        address.setCity(updateAddress.getCity());
        address.setPin(updateAddress.getPin());

        Salary salary = employee.getSalary();
        Salary updateSalary = update.getSalary();
//			PF
        salary.setPf((int) (updateSalary.getBasicSal() * 0.12));

//			Professional TAx
        if (salary.getBasicSal() >= 15000) {
            salary.setProfesionalTax(200);
        } else if (salary.getBasicSal() >= 10000) {
            salary.setProfesionalTax(150);
        } else {
            salary.setProfesionalTax(0);
        }

//			Pay Slips
        List<PaySlip> paySlips = employee.getPaySlips();
        employee.setPaySlips(paySlips);
         employeeRepo.save(employee);
         return "Updated Successfully";
    }


    public String addNewPaySlipUsingEmpId(int id, PaySlip paySlip) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));
        Salary salary = employee.getSalary();

        paySlip.setGrossPay(salary.getBasicSal() + paySlip.getTotalAllowance());
        paySlip.setNetPay(paySlip.getGrossPay() - salary.getPf() - salary.getProfesionalTax());
        paySlip.setTotalSalary(
                salary.getBasicSal() + paySlip.getTotalAllowance() + salary.getPf() + salary.getProfesionalTax());

        List<PaySlip> paySlips = employee.getPaySlips();
        paySlips.add(paySlip);
        employee.setPaySlips(paySlips);
        employeeRepo.save(employee);
        return "Pay Slip Added";
    }

    public List<PaySlipsResponceDto> getEmpPaySlips(int id) {
        Employee employee = employeeRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));
        List<PaySlip> paySlips = employee.getPaySlips();
        List<PaySlipsResponceDto> paySlipsResponceDtos = paySlips.stream()
                .map(this::convertEntityResponse)
                .collect(Collectors.toList());
        return paySlipsResponceDtos;
    }

    public List<EmplyoeeResponseDto> getAll(){
        List<Employee> employees=employeeRepo.findAll();
        List<EmplyoeeResponseDto> emplyoeeResponseDtos=new ArrayList<EmplyoeeResponseDto>();
        for (Employee employee:employees) {
            EmplyoeeResponseDto emplyoeeResponseDto=new EmplyoeeResponseDto();
            emplyoeeResponseDto.setEmpId(employee.getEmpId());
            emplyoeeResponseDto.setArea(employee.getAddress().getArea());
            emplyoeeResponseDto.setEmail(employee.getEmail());
            emplyoeeResponseDto.setPin(employee.getAddress().getPin());
            emplyoeeResponseDto.setDeptId(employee.getDept().getDeptId());
            emplyoeeResponseDto.setCity(employee.getAddress().getCity());
            emplyoeeResponseDto.setBasicSal(employee.getSalary().getBasicSal());
            List<PaySlip> paySlips=employee.getPaySlips();
            List<PaySlipsResponceDto> paySlipsResponceDtos=new ArrayList<PaySlipsResponceDto>();
            for (PaySlip paySlip:paySlips) {
                PaySlipsResponceDto paySlipsResponceDto=new PaySlipsResponceDto();
                paySlipsResponceDto.setPayShilpId(paySlip.getPayShilpId());
                paySlipsResponceDto.setTotalSalary(paySlip.getTotalSalary());
                paySlipsResponceDto.setYearMonth(paySlip.getYearMonth());
                paySlipsResponceDtos.add(paySlipsResponceDto);
            }
            emplyoeeResponseDto.setPaySlips(paySlipsResponceDtos);
            emplyoeeResponseDtos.add(emplyoeeResponseDto);
        }
        return emplyoeeResponseDtos;
    }

    public List<EmplyoeeResponseDto> getAllEmpInDeptId(int id){
        Dept dept=deptRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Inavali Dept No: "+id));
        List<Employee> employees=dept.getEmployees();
        List<EmplyoeeResponseDto> emplyoeeResponseDtos=new ArrayList<EmplyoeeResponseDto>();
        for (Employee employee:employees) {
            EmplyoeeResponseDto emplyoeeResponseDto=new EmplyoeeResponseDto();
            emplyoeeResponseDto.setEmpId(employee.getEmpId());
            emplyoeeResponseDto.setEmail(employee.getEmail());
            emplyoeeResponseDto.setBasicSal(employee.getSalary().getBasicSal());
            emplyoeeResponseDto.setDeptId(employee.getDept().getDeptId());
            emplyoeeResponseDto.setArea(employee.getAddress().getArea());
            emplyoeeResponseDto.setCity(employee.getAddress().getCity());
            emplyoeeResponseDto.setPin(employee.getAddress().getPin());
            List<PaySlip> paySlips=employee.getPaySlips();
            List<PaySlipsResponceDto> paySlipsResponceDtos=new ArrayList<PaySlipsResponceDto>();
            for (PaySlip paySlip:paySlips) {
                PaySlipsResponceDto paySlipsResponceDto=new PaySlipsResponceDto();
                paySlipsResponceDto.setYearMonth(paySlip.getYearMonth());
                paySlipsResponceDto.setTotalSalary(paySlip.getTotalSalary());
                paySlipsResponceDto.setPayShilpId(paySlip.getPayShilpId());
                paySlipsResponceDtos.add(paySlipsResponceDto);
            }
            emplyoeeResponseDto.setPaySlips(paySlipsResponceDtos);
            emplyoeeResponseDtos.add(emplyoeeResponseDto);
        }
        return emplyoeeResponseDtos;
    }

    public String csvFileRaeder(MultipartFile multipartFile){
        if (multipartFile.isEmpty())
            return "File is Empty";

        try (CSVReader csvReader=new CSVReader(new InputStreamReader(multipartFile.getInputStream()))){
            List<Employee> employees=new ArrayList<>();
            csvReader.skip(1);
            String []line;

            while ((line=csvReader.readNext())!=null){
                Employee employee=new Employee();
//                String []data=line.split(",");
                employee.setName(line[0]);
                employee.setEmail(line[1]);
                employee.setPh(line[2]);

                Address address=new Address();
                address.setArea(line[3]);
                address.setCity(line[4]);
                address.setPin(line[5]);

                employee.setAddress(address);

                Salary salary=new Salary();
                salary.setBasicSal(Integer.parseInt(line[6]));

//                PF
                salary.setPf((int) (salary.getBasicSal() * 0.12));

//		Professional TAx
                if (salary.getBasicSal() >= 15000) {
                    salary.setProfesionalTax(200);
                } else if (salary.getBasicSal() >= 10000) {
                    salary.setProfesionalTax(150);
                } else {
                    salary.setProfesionalTax(0);
                }
                employee.setSalary(salary);

                Dept dept=deptRepo.findById(Integer.parseInt(line[7])).get();
//                dept.setDeptId(Integer.parseInt(line[7]));

                employee.setDept(dept);
                employees.add(employee);
            }
            employeeRepo.saveAll(employees);
        }catch (IOException i){
            System.out.println("erorr");

        }

        return "Sccussfull";
    }
}
