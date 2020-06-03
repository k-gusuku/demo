package com.example.demo.base.controller;

import com.example.demo.base.domain.memberInformation.MemberForm;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

public interface MemberInformationController {

    /**
     * 会員情報画面遷移用getメソッド
     *
     * @param model model
     * @return 会員情報画面へ遷移
     */
    String getMemberInformation(Model model);

    /**
     * 新規会員登録画面用getメソッド
     *
     * @param form フォーム
     * @param model model
     * @return 新規会員登録画面へ遷移
     */
    public String getNewMemberRegistration(@ModelAttribute MemberForm form, Model model);

    /**
     * 新規会員登録画面用postメソッド
     *
     * @param form フォーム
     * @param bindingResult
     * @param model model
     * @return 新規会員登録画面へ遷移
     */
    public String postNewMemberRegistration(@ModelAttribute MemberForm form, BindingResult bindingResult, Model model);

    /**
     * 会員詳細画面用getメソッド
     *
     * @param memberForm フォーム
     * @param model model
     * @param memberId 会員ID
     * @return 会員詳細画面用へ遷移
     */
    public String getMemberDetail(@ModelAttribute MemberForm memberForm, Model model, @PathVariable("id") String memberId);

    /**
     * 更新画面用postメソッド
     *
     * @param memberForm フォーム
     * @param model model
     * @return 更新画面へ遷移
     */
    public String postMemberDetailUpdate(@ModelAttribute MemberForm memberForm,Model model);

    /**
     * 削除画面用postメソッド
     *
     * @param memberForm フォーム
     * @param model model
     * @return 削除画面へ遷移
     */
    public String postMemberDelete(@ModelAttribute MemberForm memberForm, Model model);
}
