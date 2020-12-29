package com.example.demo.base.conversion.product;

import com.example.demo.base.dao.product.ProductDto;
import com.example.demo.base.domain.product.ProductForm;

public interface ProductConversion {

    ProductDto form2Dto(ProductForm productForm);
    ProductForm dto2Form(ProductDto productDto);
}
