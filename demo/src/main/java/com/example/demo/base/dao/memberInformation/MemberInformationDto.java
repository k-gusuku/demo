package com.example.demo.base.dao.memberInformation;

import lombok.Data;

import java.util.Date;

@Data
public class MemberInformationDto {

    private String memberId;
    private String memberName;
    private Date birthday;
    private int age;
    private int phoneNumber;
    private String address;
    private int productHistoryId;
    private int responsibleEmployeeId;
}
