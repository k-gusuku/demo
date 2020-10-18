package com.example.demo.base.service.impl;

import com.example.demo.base.dao.employeeInformation.EmployeeInformationDao;
import com.example.demo.base.dao.employeeInformation.EmployeeInformationDto;
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

    @Autowired
    @Qualifier("EmployeeInformationDaoImpl")
    EmployeeInformationDao dao;

    @Override
    public EmployeeInformationDto selectOne(String employeeId) {

        //selectOne実行
        return dao.selectOne(employeeId);
    }

    @Override
    public List<EmployeeInformationDto> selectMany(String employeeId, String employeeName) {
        return dao.selectMany(employeeId, employeeName);
    }

    @Override
    public boolean insertOne(EmployeeInformationDto employeeInformationDto) {

        int rowNumber = dao.insertOne(employeeInformationDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateOne(EmployeeInformationDto employeeInformationDto) {

        int rowNumber = dao.updateOne(employeeInformationDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteOne(String employeeId) {

        int rowNumber = dao.deleteOne(employeeId);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public void employeeCsvOut() throws DataAccessException {
        //CSV出力
        dao.employeeCsvOut();
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
