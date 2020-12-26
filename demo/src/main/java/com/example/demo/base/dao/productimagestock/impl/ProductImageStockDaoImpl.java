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
    @Lazy
    private final ProductImageStockDao productImageStockDao;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    public ProductImageStockDaoImpl(ProductImageStockDao productImageStockDao) {
        this.productImageStockDao = productImageStockDao;
    }

    @Override
    public ProductImageStockDto selectOne(String productImageId) throws DataAccessException {
        return productImageStockDao.selectOne(productImageId);
    }

    @Override
    public List<ProductImageStockDto> selectMany(String productImageId, String productImageName, String productImageType) throws DataAccessException {
        return productImageStockDao.selectMany(productImageId, productImageName, productImageType);
    }

    @Override
    public int insertOne(ProductImageStockDto productImageStockDto) throws DataAccessException {
        return productImageStockDao.insertOne(productImageStockDto);
    }

    @Override
    public int updateOne(ProductImageStockDto productImageStockDto) throws DataAccessException {
        return productImageStockDao.updateOne(productImageStockDto);
    }

    @Override
    public int deleteOne(String productImageId) throws DataAccessException {
        return productImageStockDao.deleteOne(productImageId);
    }

    @Override
    public void productImageStockCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM PRODUCT_IMAGE_STOCK";

        ProductImageStockRowCallbackHandler handler = new ProductImageStockRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
