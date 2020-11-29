package com.example.demo.base.dao.employeeinformation.impl;


import com.example.demo.base.dao.employeeinformation.EmployeeInformationDao;
import com.example.demo.base.dao.employeeinformation.EmployeeInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("EmployeeInformationDaoImpl")
public class EmployeeInformationDaoImpl implements EmployeeInformationDao {

    @Autowired
    @Lazy
    EmployeeInformationDao dao;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<EmployeeInformationDto> selectMany(String employeeId, String employeeName) throws DataAccessException {
        return dao.selectMany(employeeId, employeeName);
    }

    @Override
    public EmployeeInformationDto selectOne(String employeeId) throws DataAccessException {
        return dao.selectOne(employeeId);
    }

    @Override
    public int insertOne(EmployeeInformationDto employeeInformationDto) throws DataAccessException {

        String password = passwordEncoder.encode(employeeInformationDto.getPassword());
        employeeInformationDto.setPassword(password);

        return dao.insertOne(employeeInformationDto);
    }

    @Override
    public int updateOne(EmployeeInformationDto employeeInformationDto) throws DataAccessException {
        return dao.updateOne(employeeInformationDto);
    }

    @Override
    public int deleteOne(String employeeId) throws DataAccessException {
        return dao.deleteOne(employeeId);
    }

    @Override
    public void employeeCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM EMPLOYEE";

        EmployeeRowCallbackHandler handler = new EmployeeRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
