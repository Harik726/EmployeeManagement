package com.p3Solutions.EmployeeP3.department.service;

import com.p3Solutions.EmployeeP3.department.entity.Dept;
import com.p3Solutions.EmployeeP3.department.dto.DeptRequestDto;
import com.p3Solutions.EmployeeP3.department.dto.DeptResponseDto;
import com.p3Solutions.EmployeeP3.payslip.dto.PaySlipsResponceDto;
import com.p3Solutions.EmployeeP3.payslip.entity.PaySlip;
import com.p3Solutions.EmployeeP3.department.repo.DeptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeptService {
    @Autowired
    private DeptRepo deptRepo;

    public String saveDept(DeptRequestDto deptRequestDto){
        Dept dept=new Dept();
        dept.setDeptName(deptRequestDto.getDeptName());
        dept.setCoordinator(deptRequestDto.getCoordinator());
        deptRepo.save(dept);
        return "Dept Saved";

    }


    public DeptResponseDto getDeptByIdService(int id){
        Dept dept=deptRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid Dept Id: "+id));
        DeptResponseDto deptResponseDto=new DeptResponseDto();
        deptResponseDto.setDeptId(dept.getDeptId());
        deptResponseDto.setDeptName(dept.getDeptName());
        deptResponseDto.setCoordinator(dept.getCoordinator());

        return deptResponseDto;
    }



    public String deleteDeptById(int id){
        Dept dept=deptRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid Dept Number or Already Deleted: "+id));
        if (dept.getDeptId()==id){
            deptRepo.deleteById(id);
            return "Deleted Successfully";

        }
        return "Null";
    }

    public String updateDeptById(int id,DeptRequestDto deptRequestDto){
        Dept dept=deptRepo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid Dept Id: "+id));
        dept.setDeptName(deptRequestDto.getDeptName());
        dept.setCoordinator(deptRequestDto.getCoordinator());
        dept.setEmployees(dept.getEmployees());
        deptRepo.save(dept);

        return "Updated";
    }

    public List<DeptResponseDto> getAll(){
        List<Dept> depts=deptRepo.findAll();
        List<DeptResponseDto> deptResponseDtos=new ArrayList<DeptResponseDto>();
        for (Dept dept:depts) {
            DeptResponseDto  deptResponseDto=new DeptResponseDto();
            deptResponseDto.setDeptId(dept.getDeptId());
            deptResponseDto.setDeptName(dept.getDeptName());
            deptResponseDto.setCoordinator(dept.getCoordinator());
            deptResponseDtos.add(deptResponseDto);
        }
        return deptResponseDtos;
    }

}
