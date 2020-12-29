package com.example.demo.base.service.impl;

import com.example.demo.base.dao.employee.EmployeeDto;
import com.example.demo.base.dao.employee.EmployeeDao;
import com.example.demo.base.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * EmployeeServiceの実装クラス.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Qualifier("EmployeeDaoImpl")
    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public EmployeeDto selectOne(String employeeId) {

        //selectOne実行
        return employeeDao.selectOne(employeeId);
    }

    @Override
    public List<EmployeeDto> selectMany(String employeeId, String employeeName) {
        return employeeDao.selectMany(employeeId, employeeName);
    }

    @Override
    public boolean insertOne(EmployeeDto employeeDto) {
        String password = passwordEncoder.encode(employeeDto.getPassword());
        employeeDto.setPassword(password);

        int rowNumber = employeeDao.insertOne(employeeDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateOne(EmployeeDto employeeDto) {
        int rowNumber = employeeDao.updateOne(employeeDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteOne(String employeeId) {
        int rowNumber = employeeDao.deleteOne(employeeId);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public void employeeCsvOut() throws DataAccessException {
        //CSV出力
        employeeDao.employeeCsvOut();
    }

    @Override
    public byte[] getFile(String fileName) throws IOException {
        // ファイルシステム(デフォルト)の取得
        FileSystem fs = FileSystems.getDefault();

        // ファイル取得
        Path p = fs.getPath(fileName);

        // ファイルをbyte配列に変換
        byte[] bytes = Files.readAllBytes(p);

        return bytes;
    }
}
