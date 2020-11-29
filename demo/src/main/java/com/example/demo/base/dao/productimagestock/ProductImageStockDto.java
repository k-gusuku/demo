package com.example.demo.base.dao.productimagestock;

import lombok.Data;

@Data
public class ProductImageStockDto {

    /**
     * 商品イメージID
     */
    private String productImageId;

    /**
     * 商品イメージ名
     */
    private String productImageName;

    /**
     * 商品イメージの種類
     */
    private String productImageType;

    // 画像表示用
    private String productImage;
}
