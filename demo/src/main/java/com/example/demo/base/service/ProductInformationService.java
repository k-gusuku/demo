package com.example.demo.base.service;

import com.example.demo.base.dao.productInformation.ProductInformationDto;

import java.util.List;

public interface ProductInformationService {

    /**
     * 商品テーブルからデータを取得する.
     *
     * @param productId 商品ID
     * @param productName 商品名
     * @return 取得データ
     */
    List<ProductInformationDto> selectMany(String productId, String productName);
}
