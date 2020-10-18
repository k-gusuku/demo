package com.example.demo.base.dao.employeeInformation.impl;


import com.example.demo.base.dao.employeeInformation.EmployeeInformationDao;
import com.example.demo.base.dao.employeeInformation.EmployeeInformationDto;
import com.example.demo.base.domain.employee.EmployeeForm;
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
    public int updateOne(EmployeeForm employeeForm) throws DataAccessException {
        return dao.updateOne(employeeForm);
    }

    @Override
    public int deleteOne(String employeeId) throws DataAccessException {
        return dao.deleteOne(employeeId);
    }

    @Override
    public int insertOne(EmployeeForm employeeForm) throws DataAccessException {

        String password = passwordEncoder.encode(employeeForm.getPassword());
        employeeForm.setPassword(password);

        return dao.insertOne(employeeForm);
    }

    @Override
    public void employeeCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM EMPLOYEE";

        EmployeeRowCallbackHandler handler = new EmployeeRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
