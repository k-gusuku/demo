package com.example.demo.base.service.impl;

import com.example.demo.base.dao.memberHistory.MemberHistoryDao;
import com.example.demo.base.dao.memberHistory.MemberHistoryDto;
import com.example.demo.base.service.MemberHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberHistoryServiceImpl implements MemberHistoryService {

    @Autowired
    @Qualifier("MemberHistoryDaoImpl")
    MemberHistoryDao dao;

    @Override
    public List<MemberHistoryDto> selectMemberHistory(String memberId) {
        return dao.selectMemberHistory(memberId);
    }

    @Override
    public boolean insertOne(MemberHistoryDto memberHistoryDto) {

        int rowNumber = dao.insertOne(memberHistoryDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }
}
