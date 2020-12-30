package com.example.demo.base.conversion.memberhistory;

import com.example.demo.base.dao.memberhistory.MemberHistoryDto;
import com.example.demo.base.dao.product.ProductDto;
import com.example.demo.base.domain.memberhistory.MemberHistoryForm;

public interface MemberHistoryConversion {
    MemberHistoryDto form2Dto(MemberHistoryForm memberHistoryForm);
    MemberHistoryForm dto2Form(MemberHistoryDto memberHistoryDto);
    MemberHistoryForm productDto2Form(ProductDto productDto);
}
