package com.example.demo.base.jdbc.product.impl;

import com.example.demo.base.jdbc.product.ProductJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductJdbcImpl implements ProductJdbc {

    private final JdbcTemplate jdbc;

    @Autowired
    public ProductJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void productCsvOut() throws DataAccessException {
        String sql = "SELECT * FROM PRODUCT";

        ProductRowCallbackHandler handler = new ProductRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
