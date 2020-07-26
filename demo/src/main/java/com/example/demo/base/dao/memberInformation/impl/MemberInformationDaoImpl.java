package com.example.demo.base.dao.memberInformation.impl;

import com.example.demo.base.dao.memberInformation.MemberInformationDao;
import com.example.demo.base.dao.memberInformation.MemberInformationDto;
import com.example.demo.base.domain.memberInformation.MemberForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MemberInformationDaoImpl")
public class MemberInformationDaoImpl implements MemberInformationDao {

    @Autowired
    @Lazy
    MemberInformationDao dao;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int insertOne(MemberForm memberForm) throws DataAccessException {
        return dao.insertOne(memberForm);
    }

    @Override
    public MemberInformationDto selectOne(String memberId) throws DataAccessException {
        return dao.selectOne(memberId);
    }

    @Override
    public List<MemberInformationDto> selectMany(String memberId, String memberName) throws DataAccessException {
        return dao.selectMany(memberId, memberName);
    }

    @Override
    public int updateOne(MemberForm memberForm) throws DataAccessException {
        return dao.updateOne(memberForm);
    }

    @Override
    public int deleteOne(String memberId) throws DataAccessException {
        return dao.deleteOne(memberId);
    }

    public void userCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM member";

        MemberRowCallbackHandler handoler = new MemberRowCallbackHandler();

        jdbc.query(sql, handoler);
    }
}
