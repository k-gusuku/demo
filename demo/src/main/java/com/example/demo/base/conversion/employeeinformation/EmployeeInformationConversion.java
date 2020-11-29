package com.example.demo.base.conversion.employeeinformation;

import com.example.demo.base.dao.employeeinformation.EmployeeInformationDto;
import com.example.demo.base.domain.employee.EmployeeForm;

public interface EmployeeInformationConversion {
    EmployeeInformationDto form2Dto(EmployeeForm employeeForm);
    EmployeeForm dto2Form(EmployeeInformationDto employeeInformationDto);
}
