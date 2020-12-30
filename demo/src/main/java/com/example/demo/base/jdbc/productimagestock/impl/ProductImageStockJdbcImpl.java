package com.example.demo.base.jdbc.productimagestock.impl;

import com.example.demo.base.jdbc.productimagestock.ProductImageStockJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductImageStockJdbcImpl implements ProductImageStockJdbc {

    private final JdbcTemplate jdbc;

    @Autowired
    public ProductImageStockJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void productImageStockCsvOut() throws DataAccessException {
        String sql = "SELECT * FROM PRODUCT_IMAGE_STOCK";

        ProductImageStockRowCallbackHandler handler = new ProductImageStockRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
