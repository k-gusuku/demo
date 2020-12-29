package com.example.demo.base.dao.member;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * memberテーブルのDataTransferObject.
 */
@Data
public class MemberDto {

    /**
     * 会員ID
     */
    private String memberId;

    /**
     * パスワード
     */
    private String password;

    /**
     * 会員名
     */
    private String memberName;

    /**
     * 権限
     */
    private String role;

    /**
     * 生年月日
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    /**
     * 年齢
     */
    private Long age;

    /**
     * 電話番号
     */
    private String phoneNumber;

    /**
     * 住所
     */
    private String address;
}
