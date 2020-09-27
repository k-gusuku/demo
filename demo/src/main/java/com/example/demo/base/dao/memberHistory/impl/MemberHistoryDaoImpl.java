package com.example.demo.base.dao.memberHistory.impl;

import com.example.demo.base.dao.memberHistory.MemberHistoryDao;
import com.example.demo.base.dao.memberHistory.MemberHistoryDto;
import com.example.demo.base.domain.memberHistory.MemberHistoryForm;
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
    public int insertOne(MemberHistoryForm memberHistoryForm) throws DataAccessException {
        return dao.insertOne(memberHistoryForm);
    }
}
