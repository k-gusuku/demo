package com.example.demo.base.domain.productimagestock;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductImageStockForm {

    /**
     * 商品イメージID
     */
    @NotBlank(groups = ProductImageStockValidGroup1.class)
    private String productImageId;

    /**
     * 商品イメージ名
     */
    @NotBlank(groups = ProductImageStockValidGroup1.class)
    private String productImageName;

    /**
     * 商品イメージの種類
     */
    @NotBlank(groups = ProductImageStockValidGroup1.class)
    private String productImageType;

    // 画像表示用
    private String productImage;
}
