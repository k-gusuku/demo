package com.example.demo.base.controller;

import com.example.demo.base.dao.memberHistory.MemberHistoryDto;
import com.example.demo.base.dao.memberInformation.MemberInformationDto;
import com.example.demo.base.domain.memberInformation.MemberForm;
import com.example.demo.base.service.impl.MemberHistoryServiceImpl;
import com.example.demo.base.service.impl.MemberInformationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class MemberInformationController {

    @Autowired
    MemberInformationServiceImpl memberInformationServiceImpl;

    @Autowired
    MemberHistoryServiceImpl memberHistoryService;

    // 会員情報取得のGET用メソッド
    @GetMapping("/memberInformation_contents")
    public String getMemberInformation(@ModelAttribute MemberForm form, Model model) {

        model.addAttribute("contents", "base/member/memberInformation::memberInformation_contents");

        List<MemberInformationDto> memberInformationDtoList = memberInformationServiceImpl.selectMany(form.getMemberId(), form.getMemberName());

        model.addAttribute("memberInformationDtoList", memberInformationDtoList);

        return "base/homeLayout";
    }

    // 会員詳細画面のGETメソッド
    @GetMapping("/memberDetail/{id:.+}")
    public String getMemberDetail(@ModelAttribute MemberForm memberForm, Model model, @PathVariable("id") String memberId) {

        System.out.println("memberId =" + memberId);

        model.addAttribute("contents", "base/member/memberDetail::memberDetail_contents");

        if (memberId != null && memberId.length() > 0) {

            MemberInformationDto memberInformationDto = memberInformationServiceImpl.selectOne(memberId);

            List<MemberHistoryDto> memberHistoryDtoList = memberHistoryService.selectMemberHistory(memberId);

            memberForm.setMemberId(memberInformationDto.getMemberId()); //会員ID
            memberForm.setMemberName(memberInformationDto.getMemberName()); //会員名
            memberForm.setBirthday(memberInformationDto.getBirthday()); //生年月日
            memberForm.setAge(memberInformationDto.getAge()); //年齢
            memberForm.setPhoneNumber(memberInformationDto.getPhoneNumber()); //電話番号
            memberForm.setAddress(memberInformationDto.getAddress()); //住所

            model.addAttribute("memberForm", memberForm);
            model.addAttribute("memberHistoryDtoList", memberHistoryDtoList);
        }
        return "base/homeLayout";
    }

    // 新規会員登録のGETメソッド
    @GetMapping("/newMemberRegistration_contents")
    public String getNewMemberRegistration(@ModelAttribute MemberForm form, Model model) {
        model.addAttribute("contents", "base/member/newMemberRegistration::newMemberRegistration_contents");
        return "base/homeLayout";
    }

    // 新規会員登録のPOSTメソッド
    @PostMapping("/newMemberRegistration_contents")
    public String postNewMemberRegistration(@ModelAttribute MemberForm memberForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getNewMemberRegistration(memberForm, model);
        }
        System.out.println(memberForm);

        MemberInformationDto memberInformationDto = new MemberInformationDto();

        memberInformationDto.setMemberId(memberForm.getMemberId()); // 会員ID
        memberInformationDto.setPassword(memberForm.getPassword()); // パスワード
        memberInformationDto.setMemberName(memberForm.getMemberName()); // 会員名
        memberInformationDto.setRole("ROLE_GENERAL"); // 権限
        memberInformationDto.setBirthday(memberForm.getBirthday()); // 生年月日
        memberInformationDto.setAge(memberForm.getAge()); // 年齢
        memberInformationDto.setPhoneNumber(memberForm.getPhoneNumber()); // 電話番号
        memberInformationDto.setAddress(memberForm.getAddress()); // 住所

        boolean result = memberInformationServiceImpl.insertOne(memberInformationDto);

        // 会員登録結果の判定
        if (result = true) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }

        // 会員情報画面の検索結果用に従業員IDと従業員名を空にする。
        memberForm.setMemberId("");
        memberForm.setMemberName("");

        return getMemberInformation(memberForm, model);
    }

    // 更新用のpostメソッド
    @PostMapping(value = "/memberDetail", params = "update")
    public String postMemberDetailUpdate(@ModelAttribute MemberForm memberForm, Model model) {
        System.out.println("更新ボタンの処理");

        MemberInformationDto memberInformationDto = new MemberInformationDto();
        memberInformationDto.setMemberId(memberForm.getMemberId());
        memberInformationDto.setMemberName(memberForm.getMemberName());
        memberInformationDto.setBirthday(memberForm.getBirthday());
        memberInformationDto.setAge(memberForm.getAge());
        memberInformationDto.setPhoneNumber(memberForm.getPhoneNumber());
        memberInformationDto.setAddress(memberForm.getAddress());

        boolean result = memberInformationServiceImpl.updateOne(memberInformationDto);

        if (result) {
            model.addAttribute("result", "更新成功");
        } else {
            model.addAttribute("result", "更新失敗");
        }

        // 会員情報画面の検索結果用に従業員IDと従業員名を空にする。
        memberForm.setMemberId("");
        memberForm.setMemberName("");

        return getMemberInformation(memberForm, model);
    }

    // 会員情報削除画面のPOSTメソッド
    @PostMapping(value = "/memberDetail", params = "delete")
    public String postMemberDelete(@ModelAttribute MemberForm memberForm, Model model) {

        System.out.println("削除ボタンの処理");

        boolean result = memberInformationServiceImpl.deleteOne(memberForm.getMemberId());

        if (result) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }

        // 会員情報画面の検索結果用に従業員IDと従業員名を空にする。
        memberForm.setMemberId("");
        memberForm.setMemberName("");

        return getMemberInformation(memberForm, model);
    }

    // 会員一覧のCSV出力処理
    @GetMapping("/memberList/csv")
    public ResponseEntity<byte[]> getMemberListCsv(Model model) {

        // 会員を全件取得して、CSVをサーバーに保存する
        memberInformationServiceImpl.memberCsvOut();

        byte[] bytes = null;

        try {

            bytes = memberInformationServiceImpl.getFile("memberInformation.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "member.csv");

        // memberInformation.csvを戻す
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
