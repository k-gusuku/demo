package com.example.demo.base.dao.memberhistory;

import lombok.Data;

import java.util.Date;

/**
 * memberHistoryテーブルのDataTransferObject.
 */
@Data
public class MemberHistoryDto {
    /**
     * 会員ID
     */
    private String memberId;

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
     * 販売日
     */
    private Date saleDay;
}
