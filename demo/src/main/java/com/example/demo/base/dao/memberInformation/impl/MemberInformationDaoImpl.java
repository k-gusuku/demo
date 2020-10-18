package com.example.demo.base.dao.memberInformation.impl;

import com.example.demo.base.dao.memberInformation.MemberInformationDao;
import com.example.demo.base.dao.memberInformation.MemberInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MemberInformationDaoImpl")
public class MemberInformationDaoImpl implements MemberInformationDao {

    @Autowired
    @Lazy
    MemberInformationDao dao;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public MemberInformationDto selectOne(String memberId) throws DataAccessException {
        return dao.selectOne(memberId);
    }

    @Override
    public List<MemberInformationDto> selectMany(String memberId, String memberName) throws DataAccessException {
        return dao.selectMany(memberId, memberName);
    }

    @Override
    public int insertOne(MemberInformationDto memberInformationDto) throws DataAccessException {

        String password = passwordEncoder.encode(memberInformationDto.getPassword());
        memberInformationDto.setPassword(password);

        return dao.insertOne(memberInformationDto);
    }

    @Override
    public int updateOne(MemberInformationDto memberInformationDto) throws DataAccessException {
        return dao.updateOne(memberInformationDto);
    }

    @Override
    public int deleteOne(String memberId) throws DataAccessException {
        return dao.deleteOne(memberId);
    }

    @Override
    public void memberCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM MEMBER";

        MemberRowCallbackHandler handler = new MemberRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
