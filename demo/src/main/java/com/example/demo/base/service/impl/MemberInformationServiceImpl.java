package com.example.demo.base.service.impl;

import com.example.demo.base.dao.memberinformation.MemberInformationDao;
import com.example.demo.base.dao.memberinformation.MemberInformationDto;
import com.example.demo.base.service.MemberInformationService;
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
public class MemberInformationServiceImpl implements MemberInformationService {
    @Qualifier("MemberInformationDaoImpl")
    private final MemberInformationDao memberInformationDao;

    @Autowired
    public MemberInformationServiceImpl(MemberInformationDao memberInformationDao) {
        this.memberInformationDao = memberInformationDao;
    }

    @Override
    public MemberInformationDto selectOne(String memberId) {

        //selectOne実行
        return memberInformationDao.selectOne(memberId);
    }

    public MemberInformationDto selectMember(String memberId, String memberName) {
        return memberInformationDao.selectMember(memberId, memberName);
    }

    @Override
    public List<MemberInformationDto> selectMany(String memberId, String memberName) {
        return memberInformationDao.selectMany(memberId, memberName);
    }

    @Override
    public boolean insertOne(MemberInformationDto memberInformationDto) {
        int rowNumber = memberInformationDao.insertOne(memberInformationDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateOne(MemberInformationDto memberInformationDto) {
        int rowNumber = memberInformationDao.updateOne(memberInformationDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteOne(String memberId) {
        int rowNumber = memberInformationDao.deleteOne(memberId);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public void memberCsvOut() throws DataAccessException {
        //CSV出力
        memberInformationDao.memberCsvOut();
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
