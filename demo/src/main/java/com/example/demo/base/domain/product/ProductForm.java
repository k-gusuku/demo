package com.example.demo.base.domain.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductForm {

    /**
     * 商品ID
     */
    @NotBlank(groups = ProductValidGroup1.class)
    private String productId;

    /**
     * 商品名
     */
    @NotBlank(groups = ProductValidGroup1.class)
    private String productName;

    /**
     * 商品金額
     */
    @NotNull(groups = ProductValidGroup1.class)
    private Long productPrice;

    /**
     * 商品の種類
     */
    @NotBlank(groups = ProductValidGroup1.class)
    private String productType;

    /**
     * 商品イメージID
     */
    private String productImageId;

    /**
     * 会員ID
     */
    private String memberId;

    // 検索用商品イメージID
    private String searchForProductImageId;
    // 検索用商品イメージ名
    private String searchForProductImageName;
    // 検索用商品イメージの種類
    private String searchForProductImageType;
}
