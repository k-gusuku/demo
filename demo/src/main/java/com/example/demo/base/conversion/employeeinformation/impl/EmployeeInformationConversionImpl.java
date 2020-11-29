package com.example.demo.base.conversion.employeeinformation.impl;

import com.example.demo.base.conversion.employeeinformation.EmployeeInformationConversion;
import com.example.demo.base.dao.employeeinformation.EmployeeInformationDto;
import com.example.demo.base.domain.employee.EmployeeForm;
import org.springframework.stereotype.Component;

@Component
public class EmployeeInformationConversionImpl implements EmployeeInformationConversion {

    @Override
    public EmployeeInformationDto form2Dto(EmployeeForm employeeForm) {
        EmployeeInformationDto employeeInformationDto = new EmployeeInformationDto();
        employeeInformationDto.setEmployeeId(employeeForm.getEmployeeId());
        employeeInformationDto.setPassword(employeeForm.getPassword());
        employeeInformationDto.setEmployeeName(employeeForm.getEmployeeName());
        employeeInformationDto.setRole(employeeForm.getRole());
        return employeeInformationDto;
    }

    @Override
    public EmployeeForm dto2Form(EmployeeInformationDto employeeInformationDto) {
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setEmployeeId(employeeInformationDto.getEmployeeId());
        employeeForm.setPassword(employeeInformationDto.getPassword());
        employeeForm.setEmployeeName(employeeInformationDto.getEmployeeName());
        employeeForm.setRole(employeeInformationDto.getRole());
        return employeeForm;
    }
}
