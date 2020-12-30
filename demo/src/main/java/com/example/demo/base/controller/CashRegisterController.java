package com.example.demo.base.controller;

import com.example.demo.base.conversion.memberhistory.MemberHistoryConversion;
import com.example.demo.base.dao.memberhistory.MemberHistoryDto;
import com.example.demo.base.domain.memberhistory.MemberHistoryForm;
import com.example.demo.base.domain.memberhistory.MemberHistoryGroupOrder;
import com.example.demo.base.service.MemberHistoryService;
import com.example.demo.base.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CashRegisterController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 未入力のStringをnullに設定する
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private final MemberHistoryService memberHistoryService;
    private final ProductService productService;
    private final MemberHistoryConversion memberHistoryConversion;

    @Autowired
    public CashRegisterController(MemberHistoryService memberHistoryService, ProductService productService, MemberHistoryConversion memberHistoryConversion) {
        this.memberHistoryService = memberHistoryService;
        this.productService = productService;
        this.memberHistoryConversion = memberHistoryConversion;
    }

    @GetMapping("/cashRegister_contents")
    public String getProduct(@ModelAttribute MemberHistoryForm memberHistoryForm, Model model) {
        List<MemberHistoryForm> memberHistoryFormList = productService.selectMany(memberHistoryForm.getProductId(), memberHistoryForm.getProductName()).stream().map(p -> {
            p.setProductImageId("img/" + p.getProductImageId());
            return memberHistoryConversion.productDto2Form(p);
        }).collect(Collectors.toList());

        model.addAttribute("contents", "base/register/cashRegister::cashRegister_contents");
        model.addAttribute("memberHistoryFormList", memberHistoryFormList);

        return "base/homeLayout";
    }

    // 商品購入画面のGETメソッド
    @GetMapping("/productPurchase/{id:.+}")
    public String getProductDetail(@ModelAttribute MemberHistoryForm memberHistoryForm, Model model, @PathVariable("id") String productId) {
        System.out.println("productId =" + productId);

        if (productId != null && productId.length() > 0) {
            memberHistoryForm = memberHistoryConversion.productDto2Form(productService.selectOne(productId));
            String imageForProductDetails = "../img/" + memberHistoryForm.getProductImageId();

            model.addAttribute("contents", "base/register/productPurchase::productPurchase_contents");
            model.addAttribute("imageForProductDetails", imageForProductDetails);
            model.addAttribute("memberHistoryForm", memberHistoryForm);
        }
        return "base/homeLayout";
    }

    // 新規商品履歴登録のPOSTメソッド
    @PostMapping(value = "/productPurchase", params = "purchase")
    public String postProductPurchase(@ModelAttribute @Validated(MemberHistoryGroupOrder.class) MemberHistoryForm memberHistoryForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            String imageForProductDetails = "../img/" + memberHistoryForm.getProductImageId();
            model.addAttribute("contents", "base/register/productPurchase::productPurchase_contents");
            model.addAttribute("imageForProductDetails", imageForProductDetails);
            return "base/homeLayout";
        }
        System.out.println(memberHistoryForm);

        MemberHistoryDto memberHistoryDto = new MemberHistoryDto();
        memberHistoryDto.setProductId(memberHistoryForm.getProductId()); // 商品ID
        memberHistoryDto.setProductName(memberHistoryForm.getProductName()); // 商品名
        memberHistoryDto.setProductPrice(memberHistoryForm.getProductPrice()); // 商品金額
        memberHistoryDto.setProductType(memberHistoryForm.getProductType()); // 商品の種類
        memberHistoryDto.setMemberId(memberHistoryForm.getMemberId()); // 会員ID
        memberHistoryDto.setProductImageId(memberHistoryForm.getProductImageId()); // 商品イメージID

        boolean memberHistoryResult = memberHistoryService.insertOne(memberHistoryDto);

        // 会員履歴登録結果の判定
        if (memberHistoryResult) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }

        System.out.println("購入商品の削除処理");

        boolean productResult = productService.deleteOne(memberHistoryForm.getProductId());

        if (productResult) {
            System.out.println("削除成功");
        } else {
            System.out.println("削除失敗");
        }

        MemberHistoryForm memberHistoryFormReturn = new MemberHistoryForm();
        return getProduct(memberHistoryFormReturn, model);
    }
}
