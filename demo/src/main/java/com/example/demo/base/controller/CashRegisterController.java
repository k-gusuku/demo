package com.example.demo.base.controller;

import com.example.demo.base.dao.productInformation.ProductInformationDto;
import com.example.demo.base.domain.memberHistory.MemberHistoryForm;
import com.example.demo.base.domain.productInformation.ProductForm;
import com.example.demo.base.service.impl.MemberHistoryServiceImpl;
import com.example.demo.base.service.impl.ProductInformationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CashRegisterController {

    @Autowired
    MemberHistoryServiceImpl memberHistoryService;

    @Autowired
    ProductInformationServiceImpl productInformationServiceImpl;

    @GetMapping("/cashRegister_contents")
    public String getProductInformation(@ModelAttribute ProductForm productForm, Model model) {

        model.addAttribute("contents", "base/register/cashRegister::cashRegister_contents");

        List<ProductInformationDto> productInformationDtoList = productInformationServiceImpl.selectMany(productForm.getProductId(), productForm.getProductName());

        model.addAttribute("productInformationDtoList", productInformationDtoList);

        return "base/homeLayout";
    }

    // 商品購入画面のGETメソッド
    @GetMapping("/productPurchase/{id:.+}")
    public String getProductDetail(@ModelAttribute ProductForm productForm, Model model, @PathVariable("id") String productId) {

        System.out.println("productId =" + productId);

        model.addAttribute("contents", "base/register/productPurchase::productPurchase_contents");

        if (productId != null && productId.length() > 0) {

            ProductInformationDto productInformationDto = productInformationServiceImpl.selectOne(productId);

            productForm.setProductId(productInformationDto.getProductId()); //商品ID
            productForm.setProductName(productInformationDto.getProductName()); //商品名
            productForm.setProductPrice(productInformationDto.getProductPrice()); //商品金額
            productForm.setProductType(productInformationDto.getProductType()); //商品の種類

            model.addAttribute("productForm", productForm);
        }
        return "base/homeLayout";
    }

    // 新規商品登録のGETメソッド
    @GetMapping("/productPurchase_contents")
    public String getProductPurchase(@ModelAttribute ProductForm ProductForm, Model model) {
        model.addAttribute("contents", "base/product/productPurchase::productPurchase_contents");
        return "base/homeLayout";
    }

    // 新規商品履歴登録のPOSTメソッド
    @PostMapping(value = "/productPurchase", params = "purchase")
    public String postProductPurchase(@ModelAttribute ProductForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getProductPurchase(form, model);
        }
        System.out.println(form);

        MemberHistoryForm memberHistoryForm = new MemberHistoryForm();

        memberHistoryForm.setProductId(form.getProductId()); // 商品ID
        memberHistoryForm.setProductName(form.getProductName()); // 商品名
        memberHistoryForm.setProductPrice(form.getProductPrice()); // 商品金額
        memberHistoryForm.setProductType(form.getProductType()); // 商品の種類
        memberHistoryForm.setMemberId(form.getMemberId()); //会員ID
        memberHistoryForm.setEmployeeId(form.getEmployeeId()); //従業員ID
        memberHistoryForm.setEmployeeName(form.getEmployeeName()); //従業員名

        boolean memberHistoryResult = memberHistoryService.insertOne(memberHistoryForm);

        // 会員履歴登録結果の判定
        if (memberHistoryResult = true) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }

        System.out.println("購入商品の削除処理");

        boolean productResult = productInformationServiceImpl.deleteOne(form.getProductId());

        if (productResult) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }

        return "base/login";
    }
}
