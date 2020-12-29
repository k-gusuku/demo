package com.example.demo.base.jdbc.employee;

import org.springframework.dao.DataAccessException;

public interface EmployeeJdbc {
    /**
     * SQL取得結果をサーバーにCSVで保存.
     *
     * @throws DataAccessException CSV保存時に投げられるエラー
     */
    void employeeCsvOut() throws DataAccessException;
}
