package com.example.demo.base.dao.productinformation.impl;

import com.example.demo.base.dao.productinformation.ProductInformationDao;
import com.example.demo.base.dao.productinformation.ProductInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProductInformationDaoImpl")
public class ProductInformationDaoImpl implements ProductInformationDao {
    @Lazy
    private final ProductInformationDao productInformationDao;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    public ProductInformationDaoImpl(ProductInformationDao productInformationDao) {
        this.productInformationDao = productInformationDao;
    }

    @Override
    public ProductInformationDto selectOne(String productId) throws DataAccessException {
        return productInformationDao.selectOne(productId);
    }

    @Override
    public List<ProductInformationDto> selectMany(String productId, String productName) throws DataAccessException {
        return productInformationDao.selectMany(productId, productName);
    }

    @Override
    public int insertOne(ProductInformationDto productInformationDto) throws DataAccessException {
        return productInformationDao.insertOne(productInformationDto);
    }

    @Override
    public int updateOne(ProductInformationDto productInformationDto) throws DataAccessException {
        return productInformationDao.updateOne(productInformationDto);
    }

    @Override
    public int deleteOne(String productId) throws DataAccessException {
        return productInformationDao.deleteOne(productId);
    }

    @Override
    public void productCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM PRODUCT";

        ProductRowCallbackHandler handler = new ProductRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
