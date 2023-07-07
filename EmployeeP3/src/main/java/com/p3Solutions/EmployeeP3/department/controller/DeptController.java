package com.p3Solutions.EmployeeP3.department.controller;

import com.p3Solutions.EmployeeP3.department.dto.DeptRequestDto;
import com.p3Solutions.EmployeeP3.department.dto.DeptResponseDto;
import com.p3Solutions.EmployeeP3.department.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @PostMapping
    public  String saveDept(@RequestBody DeptRequestDto deptRequestDto){
        return  deptService.saveDept(deptRequestDto);
    }
    @GetMapping("/{id}")
    public DeptResponseDto getDeptByID(@PathVariable int id){
        return deptService.getDeptByIdService(id);
    }
    @DeleteMapping("/{id}")
    public String deleteDeptById(@PathVariable int id){
        return deptService.deleteDeptById(id);
    }

    @PutMapping("/{id}")
    public String updateDeptById(@RequestBody DeptRequestDto deptRequestDto,@PathVariable int id){
        return  deptService.updateDeptById(id,deptRequestDto);
    }

    @GetMapping
    public List<DeptResponseDto> getAllDept(){
        return deptService.getAll();
    }

}
