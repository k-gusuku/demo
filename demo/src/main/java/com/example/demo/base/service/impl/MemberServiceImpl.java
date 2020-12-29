package com.example.demo.base.service.impl;

import com.example.demo.base.dao.member.MemberDao;
import com.example.demo.base.dao.member.MemberDto;
import com.example.demo.base.service.MemberService;
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

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Qualifier("MemberInformationDaoImpl")
    private final MemberDao memberDao;

    @Autowired
    public MemberServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public MemberDto selectOne(String memberId) {

        //selectOne実行
        return memberDao.selectOne(memberId);
    }

    public MemberDto selectOneForMember(String memberId, String memberName) {
        return memberDao.selectOneForMember(memberId, memberName);
    }

    @Override
    public List<MemberDto> selectMany(String memberId, String memberName) {
        return memberDao.selectMany(memberId, memberName);
    }

    @Override
    public boolean insertOne(MemberDto memberDto) {
        String password = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(password);

        int rowNumber = memberDao.insertOne(memberDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateOne(MemberDto memberDto) {
        int rowNumber = memberDao.updateOne(memberDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteOne(String memberId) {
        int rowNumber = memberDao.deleteOne(memberId);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public void memberCsvOut() throws DataAccessException {
        //CSV出力
        memberDao.memberCsvOut();
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
