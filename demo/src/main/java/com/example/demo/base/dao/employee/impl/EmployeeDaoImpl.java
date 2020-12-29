package com.example.demo.base.dao.employee.impl;


import com.example.demo.base.dao.employee.EmployeeDto;
import com.example.demo.base.dao.employee.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * EmployeeInformationDaoの実装クラス.
 */
@Repository("EmployeeDaoImpl")
public class EmployeeDaoImpl implements EmployeeDao {
    @Lazy
    private final EmployeeDao employeeDao;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    public EmployeeDaoImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<EmployeeDto> selectMany(String employeeId, String employeeName) throws DataAccessException {
        return employeeDao.selectMany(employeeId, employeeName);
    }

    @Override
    public EmployeeDto selectOne(String employeeId) throws DataAccessException {
        return employeeDao.selectOne(employeeId);
    }

    @Override
    public int insertOne(EmployeeDto employeeDto) throws DataAccessException {
        return employeeDao.insertOne(employeeDto);
    }

    @Override
    public int updateOne(EmployeeDto employeeDto) throws DataAccessException {
        return employeeDao.updateOne(employeeDto);
    }

    @Override
    public int deleteOne(String employeeId) throws DataAccessException {
        return employeeDao.deleteOne(employeeId);
    }

    @Override
    public void employeeCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM EMPLOYEE";

        EmployeeRowCallbackHandler handler = new EmployeeRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
