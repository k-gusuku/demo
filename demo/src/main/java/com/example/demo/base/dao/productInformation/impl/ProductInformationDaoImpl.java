package com.example.demo.base.dao.productInformation.impl;

import com.example.demo.base.dao.productInformation.ProductInformationDao;
import com.example.demo.base.dao.productInformation.ProductInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProductInformationDaoImpl")
public class ProductInformationDaoImpl implements ProductInformationDao {

    @Autowired
    @Lazy
    ProductInformationDao dao;

    @Override
    public List<ProductInformationDto> selectMany(String productId, String productName) throws DataAccessException {
        return dao.selectMany(productId, productName);
    }
}
