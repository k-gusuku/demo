package com.example.demo.base.dao.memberhistory.impl;

import com.example.demo.base.dao.memberhistory.MemberHistoryDao;
import com.example.demo.base.dao.memberhistory.MemberHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MemberHistoryDaoの実装クラス.
 */
@Repository("MemberHistoryDaoImpl")
public class MemberHistoryDaoImpl implements MemberHistoryDao {
    @Lazy
    private final MemberHistoryDao memberHistoryDao;

    @Autowired
    public MemberHistoryDaoImpl(MemberHistoryDao memberHistoryDao) {
        this.memberHistoryDao = memberHistoryDao;
    }

    @Override
    public List<MemberHistoryDto> selectMemberHistory(String memberId) throws DataAccessException {
        return memberHistoryDao.selectMemberHistory(memberId);
    }

    @Override
    public int insertOne(MemberHistoryDto memberHistoryDto) throws DataAccessException {
        return memberHistoryDao.insertOne(memberHistoryDto);
    }
}
