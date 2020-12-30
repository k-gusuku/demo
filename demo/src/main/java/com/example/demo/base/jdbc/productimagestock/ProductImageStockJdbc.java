package com.example.demo.base.jdbc.productimagestock;

import org.springframework.dao.DataAccessException;

public interface ProductImageStockJdbc {
    /**
     * SQL取得結果をサーバーにCSVで保存.
     *
     * @throws DataAccessException CSV保存時に投げられるエラー
     */
    void productImageStockCsvOut() throws DataAccessException;
}
