package com.example.demo.base.domain.employee;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmployeeForm {

    /**
     * 従業員ID
     */
    @NotBlank(groups = EmployeeValidGroup1.class)
    private String employeeId;

    /**
     * パスワード
     */
    @NotBlank(groups = EmployeeValidGroup1.class)
    private String password;

    /**
     * 従業員名
     */
    @NotBlank(groups = EmployeeValidGroup1.class)
    private String employeeName;

    /**
     * ロール
     */
    private String role;
}
