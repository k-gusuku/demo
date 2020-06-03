package com.example.demo.base.service.impl;

import java.util.List;

import com.example.demo.base.dao.memberInformation.MemberInformationDto;
import com.example.demo.base.domain.memberInformation.MemberForm;
import com.example.demo.base.service.MemberInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.demo.base.dao.memberInformation.MemberInformationDao;

@Service
public class MemberInformationServiceImpl implements MemberInformationService {

    @Autowired
    @Qualifier("MemberInformationDaoImpl")
    MemberInformationDao dao;

    @Override
    public boolean insertOne(MemberForm memberForm) {

        int rowNumber = dao.insertOne(memberForm);

        boolean result = false;

        if(rowNumber>0){
            result = true;
        }
        return result;
    }

    @Override
    public MemberInformationDto selectOne(String userId) {

        //selectOne実行
        return dao.selectOne(userId);
    }

    @Override
    public List<MemberInformationDto>
    selectMany() {
        return dao.selectMany();
    }

    @Override
    public boolean updateOne(MemberInformationDto memberInformationDto) {

        int rowNumber = dao.updateOne(memberInformationDto);

        boolean result = false;

        if(rowNumber>0){
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteOne(String memberId) {

        int rowNumber = dao.deleteOne(memberId);

        boolean result = false;

        if(rowNumber>0){
            result = true;
        }
        return result;
    }
}
