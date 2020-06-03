package com.example.demo.base.dao.memberInformation.impl;

import com.example.demo.base.dao.memberInformation.MemberInformationDao;
import com.example.demo.base.dao.memberInformation.MemberInformationDto;
import com.example.demo.base.domain.memberInformation.MemberForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MemberInformationDaoImpl")
public class MemberInformationDaoImpl implements MemberInformationDao {

    @Autowired
    @Lazy
    MemberInformationDao dao;

    @Override
    public int insertOne(MemberForm memberForm) throws DataAccessException{
        return dao.insertOne(memberForm);
    }

    @Override
    public MemberInformationDto selectOne(String memberId) throws DataAccessException{
        return dao.selectOne(memberId);
    }

    @Override
    public List<MemberInformationDto> selectMany() throws DataAccessException {
        return dao.selectMany();
    }

    @Override
    public int updateOne(MemberInformationDto memberInformationDto) throws DataAccessException {
        return dao.updateOne(memberInformationDto);
    }

    @Override
    public int deleteOne(String memberId) throws DataAccessException {
        return dao.deleteOne(memberId);
    }
}
