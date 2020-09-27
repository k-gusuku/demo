package com.example.demo.base.dao.memberHistory;

import lombok.Data;

import java.util.Date;

@Data
public class MemberHistoryDto {
    /**
     * 会員ID
     */
    private String memberId;

    /**
     * 会員名
     */
    private String memberName;

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
     * 販売日
     */
    private Date saleDay;

    /**
     * 従業員ID
     */
    private String employeeId;

    /**
     * 従業員名
     */
    private String employeeName;
}
