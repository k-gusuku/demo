package com.example.demo.base.conversion.memberinformation;

import com.example.demo.base.dao.memberinformation.MemberInformationDto;
import com.example.demo.base.domain.memberinformation.MemberForm;

public interface MemberInformationConversion {

    MemberInformationDto form2Dto(MemberForm memberForm);
    MemberForm dto2Form(MemberInformationDto memberInformationDto);
}
