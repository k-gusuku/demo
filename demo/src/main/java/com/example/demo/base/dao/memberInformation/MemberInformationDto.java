package com.example.demo.base.dao.memberInformation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class MemberInformationDto {

    /**
     * 会員ID
     */
    private String memberId;

    /**
     * 会員名
     */
    private String memberName;

    /**
     * 生年月日
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    /**
     * 年齢
     */
    private int age;

    /**
     * 電話番号
     */
    private int phoneNumber;

    /**
     * 住所
     */
    private String address;

    /**
     * 購入履歴商品ID
     */
    private int productHistoryId;

    /**
     * 販売担当
     */
    private int responsibleEmployeeId;
}
