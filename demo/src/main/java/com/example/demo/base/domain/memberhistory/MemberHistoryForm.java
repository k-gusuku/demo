package com.example.demo.base.domain.memberhistory;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * memberHistoryテーブルに関するformクラス.
 */
@Data
public class MemberHistoryForm {
    /**
     * 会員ID
     */
    @NotBlank(groups = MemberHistoryValidGroup1.class)
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

    /**
     * 商品在庫数
     */
    private Long productInventory;
}
