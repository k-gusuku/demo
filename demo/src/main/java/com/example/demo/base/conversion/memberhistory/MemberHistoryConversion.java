package com.example.demo.base.conversion.memberhistory;

import com.example.demo.base.dao.memberhistory.MemberHistoryDto;
import com.example.demo.base.dao.productinformation.ProductInformationDto;
import com.example.demo.base.domain.memberhistory.MemberHistoryForm;

public interface MemberHistoryConversion {

    MemberHistoryDto form2Dto(MemberHistoryForm memberHistoryForm);
    MemberHistoryForm dto2Form(MemberHistoryDto memberHistoryDto);
    MemberHistoryForm productInformationDto2Form(ProductInformationDto productInformationDto);

}
