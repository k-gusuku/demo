package com.example.demo.base.dao.productimagestock.impl;

import com.example.demo.base.dao.productimagestock.ProductImageStockDao;
import com.example.demo.base.dao.productimagestock.ProductImageStockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProductImageStockDaoImpl")
public class ProductImageStockDaoImpl implements ProductImageStockDao {

    @Autowired
    @Lazy
    ProductImageStockDao dao;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public ProductImageStockDto selectOne(String productImageId) throws DataAccessException {
        return dao.selectOne(productImageId);
    }

    @Override
    public List<ProductImageStockDto> selectMany(String productImageId, String productImageName, String productImageType) throws DataAccessException {
        return dao.selectMany(productImageId, productImageName, productImageType);
    }

    @Override
    public int insertOne(ProductImageStockDto productImageStockDto) throws DataAccessException {
        return dao.insertOne(productImageStockDto);
    }

    @Override
    public int updateOne(ProductImageStockDto productImageStockDto) throws DataAccessException {
        return dao.updateOne(productImageStockDto);
    }

    @Override
    public int deleteOne(String productImageId) throws DataAccessException {
        return dao.deleteOne(productImageId);
    }

    @Override
    public void productImageStockCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM PRODUCT_IMAGE_STOCK";

        ProductImageStockRowCallbackHandler handler = new ProductImageStockRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
