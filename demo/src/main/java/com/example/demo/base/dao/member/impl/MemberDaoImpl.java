package com.example.demo.base.dao.member.impl;

import com.example.demo.base.dao.member.MemberDao;
import com.example.demo.base.dao.member.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MemberDaoの実装クラス.
 */
@Repository("MemberDaoImpl")
public class MemberDaoImpl implements MemberDao {
    @Lazy
    private final MemberDao memberDao;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    public MemberDaoImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public MemberDto selectOne(String memberId) throws DataAccessException {
        return memberDao.selectOne(memberId);
    }

    @Override
    public MemberDto selectOneForMember(String memberId) throws DataAccessException {
        return memberDao.selectOneForMember(memberId);
    }

    @Override
    public List<MemberDto> selectMany(String memberId, String memberName) throws DataAccessException {
        return memberDao.selectMany(memberId, memberName);
    }

    @Override
    public int insertOne(MemberDto memberDto) throws DataAccessException {
        return memberDao.insertOne(memberDto);
    }

    @Override
    public int updateOne(MemberDto memberDto) throws DataAccessException {
        return memberDao.updateOne(memberDto);
    }

    @Override
    public int deleteOne(String memberId) throws DataAccessException {
        return memberDao.deleteOne(memberId);
    }
}
