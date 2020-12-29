package com.example.demo.base.dao.employeeinformation.impl;


import com.example.demo.base.dao.employeeinformation.EmployeeInformationDao;
import com.example.demo.base.dao.employeeinformation.EmployeeInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("EmployeeInformationDaoImpl")
public class EmployeeInformationDaoImpl implements EmployeeInformationDao {
    @Lazy
    private final EmployeeInformationDao employeeInformationDao;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    public EmployeeInformationDaoImpl(EmployeeInformationDao employeeInformationDao) {
        this.employeeInformationDao = employeeInformationDao;
    }

    @Override
    public List<EmployeeInformationDto> selectMany(String employeeId, String employeeName) throws DataAccessException {
        return employeeInformationDao.selectMany(employeeId, employeeName);
    }

    @Override
    public EmployeeInformationDto selectOne(String employeeId) throws DataAccessException {
        return employeeInformationDao.selectOne(employeeId);
    }

    @Override
    public int insertOne(EmployeeInformationDto employeeInformationDto) throws DataAccessException {
        return employeeInformationDao.insertOne(employeeInformationDto);
    }

    @Override
    public int updateOne(EmployeeInformationDto employeeInformationDto) throws DataAccessException {
        return employeeInformationDao.updateOne(employeeInformationDto);
    }

    @Override
    public int deleteOne(String employeeId) throws DataAccessException {
        return employeeInformationDao.deleteOne(employeeId);
    }

    @Override
    public void employeeCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM EMPLOYEE";

        EmployeeRowCallbackHandler handler = new EmployeeRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
