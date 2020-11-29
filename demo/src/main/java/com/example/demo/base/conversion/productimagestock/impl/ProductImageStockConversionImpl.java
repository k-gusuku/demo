package com.example.demo.base.conversion.productimagestock.impl;

import com.example.demo.base.conversion.productimagestock.ProductImageStockConversion;
import com.example.demo.base.dao.productimagestock.ProductImageStockDto;
import com.example.demo.base.domain.productimagestock.ProductImageStockForm;
import org.springframework.stereotype.Component;

@Component
public class ProductImageStockConversionImpl implements ProductImageStockConversion {

    @Override
    public ProductImageStockDto form2Dto(ProductImageStockForm productImageStockForm) {
        ProductImageStockDto productImageStockDto = new ProductImageStockDto();
        productImageStockDto.setProductImageId(productImageStockForm.getProductImageId());
        productImageStockDto.setProductImageName(productImageStockForm.getProductImageName());
        productImageStockDto.setProductImageType(productImageStockForm.getProductImageType());
        productImageStockDto.setProductImage(productImageStockForm.getProductImage());
        return productImageStockDto;
    }

    @Override
    public ProductImageStockForm dto2Form(ProductImageStockDto productImageStockDto) {
        ProductImageStockForm productImageStockForm = new ProductImageStockForm();
        productImageStockForm.setProductImageId(productImageStockDto.getProductImageId());
        productImageStockForm.setProductImageName(productImageStockDto.getProductImageName());
        productImageStockForm.setProductImageType(productImageStockDto.getProductImageType());
        productImageStockForm.setProductImage(productImageStockDto.getProductImage());
        return productImageStockForm;
    }
}
