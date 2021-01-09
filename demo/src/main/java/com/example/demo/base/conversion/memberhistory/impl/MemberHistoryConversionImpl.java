package com.example.demo.base.conversion.memberhistory.impl;

import com.example.demo.base.conversion.memberhistory.MemberHistoryConversion;
import com.example.demo.base.dao.memberhistory.MemberHistoryDto;
import com.example.demo.base.dao.product.ProductDto;
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

    @Override
    public MemberHistoryForm productDto2Form(ProductDto productDto) {
        MemberHistoryForm memberHistoryForm = new MemberHistoryForm();
        memberHistoryForm.setProductId(productDto.getProductId());
        memberHistoryForm.setProductName(productDto.getProductName());
        memberHistoryForm.setProductPrice(productDto.getProductPrice());
        memberHistoryForm.setProductType(productDto.getProductType());
        memberHistoryForm.setProductImageId(productDto.getProductImageId());
        memberHistoryForm.setProductInventory(productDto.getProductInventory());
        return memberHistoryForm;
    }
}
