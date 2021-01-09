package com.example.demo.base.dao.product;

import lombok.Data;

/**
 * productテーブルのDataTransferObject.
 */
@Data
public class ProductDto {

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 商品金額
     */
    private Long productPrice;

    /**
     * 商品の種類
     */
    private String productType;

    /**
     * 商品イメージID
     */
    private String productImageId;

    /**
     * 商品在庫数
     */
    private Long productInventory;
}
