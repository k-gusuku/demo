package com.example.demo.base.dao.employeeInformation;

import lombok.Data;

@Data
public class EmployeeInformationDto {

    /**
     * 従業員ID
     */
    private String employeeId;

    /**
     * 従業員名
     */
    private String employeeName;
}
