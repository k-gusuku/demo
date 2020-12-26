package com.example.demo.base.service.impl;

import com.example.demo.base.dao.employeeinformation.EmployeeInformationDao;
import com.example.demo.base.dao.employeeinformation.EmployeeInformationDto;
import com.example.demo.base.service.EmployeeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class EmployeeInformationServiceImpl implements EmployeeInformationService {
    @Qualifier("EmployeeInformationDaoImpl")
    private final EmployeeInformationDao employeeInformationDao;

    @Autowired
    public EmployeeInformationServiceImpl(EmployeeInformationDao employeeInformationDao) {
        this.employeeInformationDao = employeeInformationDao;
    }

    @Override
    public EmployeeInformationDto selectOne(String employeeId) {

        //selectOne実行
        return employeeInformationDao.selectOne(employeeId);
    }

    @Override
    public List<EmployeeInformationDto> selectMany(String employeeId, String employeeName) {
        return employeeInformationDao.selectMany(employeeId, employeeName);
    }

    @Override
    public boolean insertOne(EmployeeInformationDto employeeInformationDto) {
        int rowNumber = employeeInformationDao.insertOne(employeeInformationDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateOne(EmployeeInformationDto employeeInformationDto) {
        int rowNumber = employeeInformationDao.updateOne(employeeInformationDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteOne(String employeeId) {
        int rowNumber = employeeInformationDao.deleteOne(employeeId);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public void employeeCsvOut() throws DataAccessException {
        //CSV出力
        employeeInformationDao.employeeCsvOut();
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
