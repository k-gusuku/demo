package com.example.demo.base.conversion.productimagestock;

import com.example.demo.base.dao.productimagestock.ProductImageStockDto;
import com.example.demo.base.domain.productimagestock.ProductImageStockForm;

public interface ProductImageStockConversion {

    ProductImageStockDto form2Dto(ProductImageStockForm productImageStockForm);
    ProductImageStockForm dto2Form(ProductImageStockDto productImageStockDto);
}
