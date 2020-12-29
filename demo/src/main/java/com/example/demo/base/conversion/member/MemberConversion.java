package com.example.demo.base.conversion.member;

import com.example.demo.base.dao.member.MemberDto;
import com.example.demo.base.domain.member.MemberForm;

public interface MemberConversion {

    MemberDto form2Dto(MemberForm memberForm);
    MemberForm dto2Form(MemberDto memberDto);
}
