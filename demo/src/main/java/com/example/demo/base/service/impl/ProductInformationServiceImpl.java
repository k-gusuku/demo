package com.example.demo.base.service.impl;

import com.example.demo.base.dao.productInformation.ProductInformationDao;
import com.example.demo.base.dao.productInformation.ProductInformationDto;
import com.example.demo.base.service.ProductInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInformationServiceImpl implements ProductInformationService {

    @Autowired
    @Qualifier("ProductInformationDaoImpl")
    ProductInformationDao dao;

    public List<ProductInformationDto> selectMany(String productId, String productName) {
        return dao.selectMany(productId, productName);
    }
}
