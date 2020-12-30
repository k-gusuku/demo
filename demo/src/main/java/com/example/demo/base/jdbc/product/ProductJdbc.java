package com.example.demo.base.jdbc.product;

import org.springframework.dao.DataAccessException;

public interface ProductJdbc {
    /**
     * SQL取得結果をサーバーにCSVで保存.
     *
     * @throws DataAccessException CSV保存時に投げられるエラー
     */
    void productCsvOut() throws DataAccessException;
}
