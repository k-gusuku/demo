package com.example.demo.base.dao.product.impl;

import com.example.demo.base.dao.product.ProductDao;
import com.example.demo.base.dao.product.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductDaoの実装クラス.
 */
@Repository("ProductDaoImpl")
public class ProductDaoImpl implements ProductDao {
    @Lazy
    private final ProductDao productDao;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    public ProductDaoImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ProductDto selectOne(String productId) throws DataAccessException {
        return productDao.selectOne(productId);
    }

    @Override
    public List<ProductDto> selectMany(String productId, String productName) throws DataAccessException {
        return productDao.selectMany(productId, productName);
    }

    @Override
    public int insertOne(ProductDto productDto) throws DataAccessException {
        return productDao.insertOne(productDto);
    }

    @Override
    public int updateOne(ProductDto productDto) throws DataAccessException {
        return productDao.updateOne(productDto);
    }

    @Override
    public int deleteOne(String productId) throws DataAccessException {
        return productDao.deleteOne(productId);
    }

    @Override
    public void productCsvOut() throws DataAccessException {

        String sql = "SELECT * FROM PRODUCT";

        ProductRowCallbackHandler handler = new ProductRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
