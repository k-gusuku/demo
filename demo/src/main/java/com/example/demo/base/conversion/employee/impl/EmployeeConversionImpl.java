package com.example.demo.base.conversion.employee.impl;

import com.example.demo.base.conversion.employee.EmployeeConversion;
import com.example.demo.base.dao.employee.EmployeeDto;
import com.example.demo.base.domain.employee.EmployeeForm;
import org.springframework.stereotype.Component;

/**
 * EmployeeConversionの実装クラス
 */
@Component
public class EmployeeConversionImpl implements EmployeeConversion {

    @Override
    public EmployeeDto form2Dto(EmployeeForm employeeForm) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId(employeeForm.getEmployeeId());
        employeeDto.setPassword(employeeForm.getPassword());
        employeeDto.setEmployeeName(employeeForm.getEmployeeName());
        employeeDto.setRole(employeeForm.getRole());
        return employeeDto;
    }

    @Override
    public EmployeeForm dto2Form(EmployeeDto employeeDto) {
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setEmployeeId(employeeDto.getEmployeeId());
        employeeForm.setPassword(employeeDto.getPassword());
        employeeForm.setEmployeeName(employeeDto.getEmployeeName());
        employeeForm.setRole(employeeDto.getRole());
        return employeeForm;
    }
}
