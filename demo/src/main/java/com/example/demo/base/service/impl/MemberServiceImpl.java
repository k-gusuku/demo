package com.example.demo.base.service.impl;

import com.example.demo.base.dao.member.MemberDao;
import com.example.demo.base.dao.member.MemberDto;
import com.example.demo.base.jdbc.member.MemberJdbc;
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

/**
 * MemberServiceの実装クラス.
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired

    @Qualifier("MemberDaoImpl")
    private final MemberDao memberDao;
    private final MemberJdbc memberJdbc;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberServiceImpl(MemberDao memberDao, MemberJdbc memberJdbc, PasswordEncoder passwordEncoder) {
        this.memberDao = memberDao;
        this.memberJdbc = memberJdbc;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public MemberDto selectOne(String memberId) {

        //selectOne実行
        return memberDao.selectOne(memberId);
    }

    @Override
    public MemberDto selectOneForMember(String memberId) {
        return memberDao.selectOneForMember(memberId);
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
        memberJdbc.memberCsvOut();
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
