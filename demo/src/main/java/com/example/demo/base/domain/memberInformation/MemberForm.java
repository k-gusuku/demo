package com.example.demo.base.domain.memberInformation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MemberForm {

    private String memberId;
    private String memberName;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthday;
    private int age;
    private int phoneNumber;
    private String address;
}
