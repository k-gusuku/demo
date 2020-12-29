package com.example.demo.base.conversion.employee;

import com.example.demo.base.dao.employee.EmployeeDto;
import com.example.demo.base.domain.employee.EmployeeForm;

public interface EmployeeConversion {
    EmployeeDto form2Dto(EmployeeForm employeeForm);
    EmployeeForm dto2Form(EmployeeDto employeeDto);
}
