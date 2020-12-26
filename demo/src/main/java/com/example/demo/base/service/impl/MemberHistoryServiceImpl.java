package com.example.demo.base.service.impl;

import com.example.demo.base.dao.memberhistory.MemberHistoryDao;
import com.example.demo.base.dao.memberhistory.MemberHistoryDto;
import com.example.demo.base.service.MemberHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MemberHistoryServiceImpl implements MemberHistoryService {
    @Qualifier("MemberHistoryDaoImpl")
    private final MemberHistoryDao memberHistoryDao;

    @Autowired
    public MemberHistoryServiceImpl(MemberHistoryDao memberHistoryDao) {
        this.memberHistoryDao = memberHistoryDao;
    }

    @Override
    public List<MemberHistoryDto> selectMemberHistory(String memberId) {
        return memberHistoryDao.selectMemberHistory(memberId);
    }

    @Override
    public boolean insertOne(MemberHistoryDto memberHistoryDto) {
        memberHistoryDto.setSaleDay(new Date());
        int rowNumber = memberHistoryDao.insertOne(memberHistoryDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }
}
