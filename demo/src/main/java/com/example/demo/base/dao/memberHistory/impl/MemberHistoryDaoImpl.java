package com.example.demo.base.dao.memberHistory.impl;

import com.example.demo.base.dao.memberHistory.MemberHistoryDao;
import com.example.demo.base.dao.memberHistory.MemberHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MemberHistoryDaoImpl")
public class MemberHistoryDaoImpl implements MemberHistoryDao {

    @Autowired
    @Lazy
    MemberHistoryDao dao;

    @Override
    public List<MemberHistoryDto> selectMemberHistory(String memberId) throws DataAccessException {
        return dao.selectMemberHistory(memberId);
    }

    @Override
    public int insertOne(MemberHistoryDto memberHistoryDto) throws DataAccessException {
        return dao.insertOne(memberHistoryDto);
    }
}
