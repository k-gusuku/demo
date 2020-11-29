package com.example.demo.base.conversion.productinformation.impl;

import com.example.demo.base.conversion.productinformation.ProductInformationConversion;
import com.example.demo.base.dao.productinformation.ProductInformationDto;
import com.example.demo.base.domain.productinformation.ProductForm;
import org.springframework.stereotype.Component;

@Component
public class ProductInformationConversionImpl implements ProductInformationConversion {

    @Override
    public ProductInformationDto form2Dto(ProductForm productForm) {
        ProductInformationDto productInformationDto = new ProductInformationDto();
        productInformationDto.setProductId(productForm.getProductId());
        productInformationDto.setProductName(productForm.getProductName());
        productInformationDto.setProductPrice(productForm.getProductPrice());
        productInformationDto.setProductType(productForm.getProductType());
        productInformationDto.setProductImageId(productForm.getProductImageId());
        return productInformationDto;
    }

    @Override
    public ProductForm dto2Form(ProductInformationDto productInformationDto) {
        ProductForm productForm = new ProductForm();
        productForm.setProductId(productInformationDto.getProductId());
        productForm.setProductName(productInformationDto.getProductName());
        productForm.setProductPrice(productInformationDto.getProductPrice());
        productForm.setProductType(productInformationDto.getProductType());
        productForm.setProductImageId(productInformationDto.getProductImageId());
        return productForm;
    }
}
