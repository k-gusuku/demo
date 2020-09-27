package com.example.demo.base.dao.memberInformation;

import com.example.demo.base.dao.memberHistory.MemberHistoryDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

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

    // 関連エンティティ
    private List<MemberHistoryDto> memberHistories;
}
