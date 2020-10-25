package com.example.demo.base.dao.productInformation.impl;

import com.example.demo.base.dao.productInformation.ProductInformationDao;
import com.example.demo.base.dao.productInformation.ProductInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProductInformationDaoImpl")
public class ProductInformationDaoImpl implements ProductInformationDao {

    @Autowired
    @Lazy
    ProductInformationDao dao;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public ProductInformationDto selectOne(String productId) throws DataAccessException {
        return dao.selectOne(productId);
    }

    @Override
    public List<ProductInformationDto> selectMany(String productId, String productName) throws DataAccessException {
        return dao.selectMany(productId, productName);
    }

    @Override
    public int insertOne(ProductInformationDto productInformationDto) throws DataAccessException {
        return dao.insertOne(productInformationDto);
    }

    @Override
    public int updateOne(ProductInformationDto productInformationDto) throws DataAccessException {
        return dao.updateOne(productInformationDto);
    }

    @Override
    public int deleteOne(String productId) throws DataAccessException {
        return dao.deleteOne(productId);
    }

    @Override
    public void productCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM PRODUCT";

        ProductRowCallbackHandler handler = new ProductRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
