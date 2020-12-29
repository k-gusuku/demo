package com.example.demo.base.conversion.memberinformation.impl;

import com.example.demo.base.conversion.memberinformation.MemberInformationConversion;
import com.example.demo.base.dao.memberinformation.MemberInformationDto;
import com.example.demo.base.domain.memberinformation.MemberForm;
import org.springframework.stereotype.Component;

@Component
public class MemberInformationConversionImpl implements MemberInformationConversion {

    @Override
    public MemberInformationDto form2Dto(MemberForm memberForm) {
        MemberInformationDto memberInformationDto = new MemberInformationDto();
        memberInformationDto.setMemberId(memberForm.getMemberId());
        memberInformationDto.setPassword(memberForm.getPassword());
        memberInformationDto.setMemberName(memberForm.getMemberName());
        memberInformationDto.setRole(memberForm.getRole());
        memberInformationDto.setBirthday(memberForm.getBirthday());
        memberInformationDto.setAge(memberForm.getAge());
        memberInformationDto.setPhoneNumber(memberForm.getPhoneNumber());
        memberInformationDto.setAddress(memberForm.getAddress());
        return memberInformationDto;
    }

    @Override
    public MemberForm dto2Form(MemberInformationDto memberInformationDto) {
        MemberForm memberForm = new MemberForm();
        memberForm.setMemberId(memberInformationDto.getMemberId());
        memberForm.setPassword(memberInformationDto.getPassword());
        memberForm.setMemberName(memberInformationDto.getMemberName());
        memberForm.setRole(memberInformationDto.getRole());
        memberForm.setBirthday(memberInformationDto.getBirthday());
        memberForm.setAge(memberInformationDto.getAge());
        memberForm.setPhoneNumber(memberInformationDto.getPhoneNumber());
        memberForm.setAddress(memberInformationDto.getAddress());
        return memberForm;
    }
}
