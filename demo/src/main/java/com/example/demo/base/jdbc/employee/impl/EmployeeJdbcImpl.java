package com.example.demo.base.jdbc.employee.impl;

import com.example.demo.base.jdbc.employee.EmployeeJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeJdbcImpl implements EmployeeJdbc {

    private final JdbcTemplate jdbc;

    @Autowired
    public EmployeeJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void employeeCsvOut() throws DataAccessException {
        String sql = "SELECT * FROM EMPLOYEE";

        EmployeeRowCallbackHandler handler = new EmployeeRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
