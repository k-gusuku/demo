package com.example.demo.base.conversion.member.impl;

import com.example.demo.base.conversion.member.MemberConversion;
import com.example.demo.base.dao.member.MemberDto;
import com.example.demo.base.domain.member.MemberForm;
import org.springframework.stereotype.Component;

@Component
public class MemberConversionImpl implements MemberConversion {

    @Override
    public MemberDto form2Dto(MemberForm memberForm) {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(memberForm.getMemberId());
        memberDto.setPassword(memberForm.getPassword());
        memberDto.setMemberName(memberForm.getMemberName());
        memberDto.setRole(memberForm.getRole());
        memberDto.setBirthday(memberForm.getBirthday());
        memberDto.setAge(memberForm.getAge());
        memberDto.setPhoneNumber(memberForm.getPhoneNumber());
        memberDto.setAddress(memberForm.getAddress());
        return memberDto;
    }

    @Override
    public MemberForm dto2Form(MemberDto memberDto) {
        MemberForm memberForm = new MemberForm();
        memberForm.setMemberId(memberDto.getMemberId());
        memberForm.setPassword(memberDto.getPassword());
        memberForm.setMemberName(memberDto.getMemberName());
        memberForm.setRole(memberDto.getRole());
        memberForm.setBirthday(memberDto.getBirthday());
        memberForm.setAge(memberDto.getAge());
        memberForm.setPhoneNumber(memberDto.getPhoneNumber());
        memberForm.setAddress(memberDto.getAddress());
        return memberForm;
    }
}
