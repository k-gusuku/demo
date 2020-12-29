package com.example.demo.base.controller;

import com.example.demo.base.conversion.memberhistory.MemberHistoryConversion;
import com.example.demo.base.conversion.member.MemberConversion;
import com.example.demo.base.dao.member.MemberDto;
import com.example.demo.base.domain.memberhistory.MemberHistoryForm;
import com.example.demo.base.domain.member.MemberForm;
import com.example.demo.base.domain.member.MemberGroupOrder;
import com.example.demo.base.service.MemberHistoryService;
import com.example.demo.base.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MemberController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 未入力のStringをnullに設定する
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private final MemberService memberService;
    private final MemberHistoryService memberHistoryService;
    private final MemberConversion memberConversion;
    private final MemberHistoryConversion memberHistoryConversion;

    @Autowired
    public MemberController(MemberService memberService, MemberHistoryService memberHistoryService, MemberConversion memberConversion, MemberHistoryConversion memberHistoryConversion) {
        this.memberService = memberService;
        this.memberHistoryService = memberHistoryService;
        this.memberConversion = memberConversion;
        this.memberHistoryConversion = memberHistoryConversion;
    }

    // 会員情報取得のGET用メソッド
    @GetMapping("/member_contents")
    public String getMember(@ModelAttribute MemberForm form, Model model) {
        model.addAttribute("contents", "base/member/member::member_contents");
        List<MemberForm> memberFormList = memberService.selectMany(form.getMemberId(), form.getMemberName()).stream().map(memberConversion::dto2Form).collect(Collectors.toList());

        model.addAttribute("memberFormList", memberFormList);
        return "base/homeLayout";
    }

    // 会員テーブルから会員が情報を取得するメソッド
    @GetMapping("/memberForMember_contents")
    public String getMemberForMember(@ModelAttribute MemberForm memberForm, Model model) {
        MemberDto memberDto = memberService.selectOneForMember(memberForm.getMemberId(), memberForm.getMemberName());

        if (memberDto != null) {
            List<MemberHistoryForm> memberHistoryFormList = memberHistoryService.selectMemberHistory(memberForm.getMemberId()).stream().map(m -> {
                m.setProductImageId("img/" + m.getProductImageId());
                return memberHistoryConversion.dto2Form(m);
            }).collect(Collectors.toList());
            memberForm = memberConversion.dto2Form(memberDto);
            model.addAttribute("memberHistoryFormList", memberHistoryFormList);
        } else {
            memberForm.setMemberId(null);
            memberForm.setMemberName(null);
        }
        model.addAttribute("contents", "base/member/memberForMember::memberForMember_contents");
        model.addAttribute("memberForm", memberForm);

        return "base/homeLayout";
    }

    // 会員詳細画面のGETメソッド
    @GetMapping("/memberDetail/{id:.+}")
    public String getMemberDetail(@ModelAttribute MemberForm memberForm, Model model, @PathVariable("id") String memberId) {
        System.out.println("memberId =" + memberId);

        if (memberId != null && memberId.length() > 0) {
            MemberDto memberDto = memberService.selectOne(memberId);
            List<MemberHistoryForm> memberHistoryFormList = memberHistoryService.selectMemberHistory(memberId).stream().map(h -> {
                h.setProductImageId("../img/" + h.getProductImageId());
                return memberHistoryConversion.dto2Form(h);
            }).collect(Collectors.toList());
            memberForm = memberConversion.dto2Form(memberDto);

            model.addAttribute("contents", "base/member/memberDetail::memberDetail_contents");
            model.addAttribute("memberForm", memberForm);
            model.addAttribute("memberHistoryFormList", memberHistoryFormList);
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
    public String postNewMemberRegistration(@ModelAttribute @Validated(MemberGroupOrder.class) MemberForm memberForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getNewMemberRegistration(memberForm, model);
        }
        System.out.println(memberForm);

        MemberDto memberDto = memberConversion.form2Dto(memberForm);
        memberDto.setRole("ROLE_GENERAL"); // 権限

        boolean result = memberService.insertOne(memberDto);

        // 会員登録結果の判定
        if (result) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }

        // 会員情報画面の検索結果用に従業員IDと従業員名を空にする。
        memberForm.setMemberId("");
        memberForm.setMemberName("");

        return getMember(memberForm, model);
    }

    // 会員用新規会員登録のGETメソッド
    @GetMapping("/newMemberRegistrationForMember")
    public String getNewMemberRegistrationForMember(@ModelAttribute MemberForm form, Model model) {

        return "base/member/newMemberRegistrationForMember";
    }

    // 会員用新規会員登録のPOSTメソッド
    @PostMapping("/newMemberRegistrationForMember")
    public String postNewMemberRegistrationForMember(@ModelAttribute @Validated(MemberGroupOrder.class) MemberForm memberForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getNewMemberRegistrationForMember(memberForm, model);
        }
        System.out.println(memberForm);

        MemberDto memberDto = memberConversion.form2Dto(memberForm);
        memberDto.setRole("ROLE_GENERAL"); // 権限

        boolean result = memberService.insertOne(memberDto);

        // 会員登録結果の判定
        if (result) {
            System.out.println("insert成功");
            model.addAttribute("result", "登録しました。");
        } else {
            System.out.println("insert失敗");
            model.addAttribute("result", "登録に失敗しました。");
        }

        // 会員情報画面の検索結果用に従業員IDと従業員名を空にする。
        memberForm.setMemberId(null);
        memberForm.setMemberName(null);
        memberForm.setBirthday(null);
        memberForm.setAge(null);
        memberForm.setPhoneNumber(null);
        memberForm.setAddress(null);

        return getNewMemberRegistrationForMember(memberForm, model);
    }

    // 更新用のpostメソッド
    @PostMapping(value = "/memberDetail", params = "update")
    public String postMemberDetailUpdate(@ModelAttribute @Validated(MemberGroupOrder.class) MemberForm memberForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() && bindingResult.getErrorCount() > 1) {
            List<MemberHistoryForm> memberHistoryFormList = memberHistoryService.selectMemberHistory(memberForm.getMemberId()).stream().map(h -> {
                h.setProductImageId("../img/" + h.getProductImageId());
                return memberHistoryConversion.dto2Form(h);
            }).collect(Collectors.toList());

            model.addAttribute("contents", "base/member/memberDetail::memberDetail_contents");
            model.addAttribute("memberHistoryFormList", memberHistoryFormList);
            return "base/homeLayout";
        }
        System.out.println("更新ボタンの処理");

        MemberDto memberDto = memberConversion.form2Dto(memberForm);

        boolean result = memberService.updateOne(memberDto);

        if (result) {
            model.addAttribute("result", "更新成功");
        } else {
            model.addAttribute("result", "更新失敗");
        }

        // 会員情報画面の検索結果用に従業員IDと従業員名を空にする。
        memberForm.setMemberId("");
        memberForm.setMemberName("");

        return getMemberDetail(memberForm, model, memberDto.getMemberId());
    }

    // 更新用のpostメソッド
    @PostMapping(value = "/memberDetailForMember", params = "updateForMember")
    public String postMemberDetailForMemberUpdate(@ModelAttribute @Validated(MemberGroupOrder.class) MemberForm memberForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() && bindingResult.getErrorCount() > 1) {
            List<MemberHistoryForm> memberHistoryFormList = memberHistoryService.selectMemberHistory(memberForm.getMemberId()).stream().map(h -> {
                h.setProductImageId("../img/" + h.getProductImageId());
                return memberHistoryConversion.dto2Form(h);
            }).collect(Collectors.toList());

            model.addAttribute("contents", "base/member/memberForMember::memberForMember_contents");
            model.addAttribute("memberHistoryFormList", memberHistoryFormList);
            return "base/homeLayout";
        }

        System.out.println("更新ボタンの処理");

        MemberDto memberDto = memberConversion.form2Dto(memberForm);

        boolean result = memberService.updateOne(memberDto);

        if (result) {
            model.addAttribute("result", "更新成功");
        } else {
            model.addAttribute("result", "更新失敗");
        }

        return getMemberForMember(memberForm, model);
    }

    // 会員情報削除画面のPOSTメソッド
    @PostMapping(value = "/memberDetailForMember", params = "delete")
    public String postMemberDelete(@ModelAttribute MemberForm memberForm, Model model) {
        System.out.println("削除ボタンの処理");
        boolean result = memberService.deleteOne(memberForm.getMemberId());

        if (result) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }

        return "base/login";
    }

    // 会員情報削除画面のPOSTメソッド
    @PostMapping(value = "/memberDetail", params = "delete")
    public String postMemberForMemberDelete(@ModelAttribute MemberForm memberForm, Model model) {
        System.out.println("削除ボタンの処理");

        boolean result = memberService.deleteOne(memberForm.getMemberId());

        if (result) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }

        // 会員情報画面の検索結果用に従業員IDと従業員名を空にする。
        memberForm.setMemberId(null);
        memberForm.setMemberName(null);
        memberForm.setBirthday(null);
        memberForm.setAge(null);
        memberForm.setPhoneNumber(null);
        memberForm.setAddress(null);

        return getMember(memberForm, model);
    }

    // 会員一覧のCSV出力処理
    @GetMapping("/memberList/csv")
    public ResponseEntity<byte[]> getMemberListCsv(Model model) {
        // 会員を全件取得して、CSVをサーバーに保存する
        memberService.memberCsvOut();

        byte[] bytes = null;

        try {

            bytes = memberService.getFile("member.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "member.csv");

        // member.csvを戻す
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
