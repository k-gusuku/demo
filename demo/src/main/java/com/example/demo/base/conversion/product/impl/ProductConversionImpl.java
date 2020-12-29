package com.example.demo.base.conversion.product.impl;

import com.example.demo.base.conversion.product.ProductConversion;
import com.example.demo.base.dao.product.ProductDto;
import com.example.demo.base.domain.product.ProductForm;
import org.springframework.stereotype.Component;

@Component
public class ProductConversionImpl implements ProductConversion {

    @Override
    public ProductDto form2Dto(ProductForm productForm) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(productForm.getProductId());
        productDto.setProductName(productForm.getProductName());
        productDto.setProductPrice(productForm.getProductPrice());
        productDto.setProductType(productForm.getProductType());
        productDto.setProductImageId(productForm.getProductImageId());
        return productDto;
    }

    @Override
    public ProductForm dto2Form(ProductDto productDto) {
        ProductForm productForm = new ProductForm();
        productForm.setProductId(productDto.getProductId());
        productForm.setProductName(productDto.getProductName());
        productForm.setProductPrice(productDto.getProductPrice());
        productForm.setProductType(productDto.getProductType());
        productForm.setProductImageId(productDto.getProductImageId());
        return productForm;
    }
}
