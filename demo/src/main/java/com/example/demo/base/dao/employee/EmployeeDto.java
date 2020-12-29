package com.example.demo.base.dao.employee;

import lombok.Data;

/**
 * employeeテーブルのDataTransferObject
 */
@Data
public class EmployeeDto {

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
