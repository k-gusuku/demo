package com.example.demo.base.dao.productInformation;

import lombok.Data;

@Data
public class ProductInformationDto {

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
}
