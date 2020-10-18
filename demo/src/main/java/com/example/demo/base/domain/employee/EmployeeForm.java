package com.example.demo.base.domain.employee;

import lombok.Data;

@Data
public class EmployeeForm {

    /**
     * 従業員ID
     */
    private String employeeId;

    /**
     * パスワード
     */
    private String password;

    /**
     * 従業員名
     */
    private String employeeName;

    /**
     * ロール
     */
    private String role;
}
