package com.example.demo.base.dao.memberinformation.impl;

import com.example.demo.base.dao.memberinformation.MemberInformationDao;
import com.example.demo.base.dao.memberinformation.MemberInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MemberInformationDaoImpl")
public class MemberInformationDaoImpl implements MemberInformationDao {
    @Lazy
    private final MemberInformationDao memberInformationDao;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    public MemberInformationDaoImpl(MemberInformationDao memberInformationDao) {
        this.memberInformationDao = memberInformationDao;
    }

    @Override
    public MemberInformationDto selectOne(String memberId) throws DataAccessException {
        return memberInformationDao.selectOne(memberId);
    }

    @Override
    public MemberInformationDto selectOneForMember(String memberId, String memberName) throws DataAccessException {
        return memberInformationDao.selectOneForMember(memberId, memberName);
    }

    @Override
    public List<MemberInformationDto> selectMany(String memberId, String memberName) throws DataAccessException {
        return memberInformationDao.selectMany(memberId, memberName);
    }

    @Override
    public int insertOne(MemberInformationDto memberInformationDto) throws DataAccessException {
        return memberInformationDao.insertOne(memberInformationDto);
    }

    @Override
    public int updateOne(MemberInformationDto memberInformationDto) throws DataAccessException {
        return memberInformationDao.updateOne(memberInformationDto);
    }

    @Override
    public int deleteOne(String memberId) throws DataAccessException {
        return memberInformationDao.deleteOne(memberId);
    }

    @Override
    public void memberCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM MEMBER";

        MemberRowCallbackHandler handler = new MemberRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
