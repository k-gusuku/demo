package com.example.demo.base.domain.member;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * memberテーブルに関するformクラス.
 */
@Data
public class MemberForm {

    /**
     * 会員ID
     */
    @NotBlank(groups = MemberValidGroup1.class)
    private String memberId;

    /**
     * パスワード
     */
    @NotBlank(groups = MemberValidGroup1.class)
    @Length(min = 8, groups = MemberGroup2.class)
    private String password;

    /**
     * 会員名
     */
    @NotBlank(groups = MemberValidGroup1.class)
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
    @NotBlank(groups = MemberValidGroup1.class)
    private String address;
}
