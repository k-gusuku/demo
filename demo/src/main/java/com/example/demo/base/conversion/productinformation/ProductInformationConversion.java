package com.example.demo.base.conversion.productinformation;

import com.example.demo.base.dao.productinformation.ProductInformationDto;
import com.example.demo.base.domain.productinformation.ProductForm;

public interface ProductInformationConversion {

    ProductInformationDto form2Dto(ProductForm productForm);
    ProductForm dto2Form(ProductInformationDto productInformationDto);
}
