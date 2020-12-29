package com.example.demo.base.conversion.memberhistory.impl;

import com.example.demo.base.conversion.memberhistory.MemberHistoryConversion;
import com.example.demo.base.dao.memberhistory.MemberHistoryDto;
import com.example.demo.base.dao.productinformation.ProductInformationDto;
import com.example.demo.base.domain.memberhistory.MemberHistoryForm;
import org.springframework.stereotype.Component;

@Component
public class MemberHistoryConversionImpl implements MemberHistoryConversion {

    @Override
    public MemberHistoryDto form2Dto(MemberHistoryForm memberHistoryForm) {
        MemberHistoryDto memberHistoryDto = new MemberHistoryDto();
        memberHistoryDto.setMemberId(memberHistoryForm.getMemberId());
        memberHistoryDto.setProductId(memberHistoryForm.getProductId());
        memberHistoryDto.setProductName(memberHistoryForm.getProductName());
        memberHistoryDto.setProductPrice(memberHistoryForm.getProductPrice());
        memberHistoryDto.setProductType(memberHistoryForm.getProductType());
        memberHistoryDto.setProductImageId(memberHistoryForm.getProductImageId());
        memberHistoryDto.setSaleDay(memberHistoryForm.getSaleDay());
        return memberHistoryDto;
    }

    @Override
    public MemberHistoryForm dto2Form(MemberHistoryDto memberHistoryDto) {
        MemberHistoryForm memberHistoryForm = new MemberHistoryForm();
        memberHistoryForm.setMemberId(memberHistoryDto.getMemberId());
        memberHistoryForm.setProductId(memberHistoryDto.getProductId());
        memberHistoryForm.setProductName(memberHistoryDto.getProductName());
        memberHistoryForm.setProductPrice(memberHistoryDto.getProductPrice());
        memberHistoryForm.setProductType(memberHistoryDto.getProductType());
        memberHistoryForm.setProductImageId(memberHistoryDto.getProductImageId());
        memberHistoryForm.setSaleDay(memberHistoryDto.getSaleDay());
        return memberHistoryForm;
    }

    public MemberHistoryForm productInformationDto2Form(ProductInformationDto productInformationDto) {
        MemberHistoryForm memberHistoryForm = new MemberHistoryForm();
        memberHistoryForm.setProductId(productInformationDto.getProductId());
        memberHistoryForm.setProductName(productInformationDto.getProductName());
        memberHistoryForm.setProductPrice(productInformationDto.getProductPrice());
        memberHistoryForm.setProductType(productInformationDto.getProductType());
        memberHistoryForm.setProductImageId(productInformationDto.getProductImageId());
        return memberHistoryForm;
    }
}
