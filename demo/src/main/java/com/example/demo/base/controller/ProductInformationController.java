package com.example.demo.base.controller;

import com.example.demo.base.dao.productInformation.ProductInformationDto;
import com.example.demo.base.domain.productInformation.ProductForm;
import com.example.demo.base.service.impl.ProductInformationServiceImpl;
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
public class ProductInformationController {

    @Autowired
    ProductInformationServiceImpl productInformationServiceImpl;

    @GetMapping("/productInformation_contents")
    public String getProductInformation(@ModelAttribute ProductForm productForm, Model model) {

        model.addAttribute("contents", "base/product/productInformation::productInformation_contents");

        List<ProductInformationDto> productInformationDtoList = productInformationServiceImpl.selectMany(productForm.getProductId(), productForm.getProductName());

        model.addAttribute("productInformationDtoList", productInformationDtoList);

        return "base/homeLayout";
    }

    // 新規商品登録のGETメソッド
    @GetMapping("/newProductRegistration_contents")
    public String getNewProductRegistration(@ModelAttribute ProductForm ProductForm, Model model) {
        model.addAttribute("contents", "base/product/newProductRegistration::newProductRegistration_contents");
        return "base/homeLayout";
    }

    // 新規商品登録のPOSTメソッド
    @PostMapping("/newProductRegistration_contents")
    public String postNewProductRegistration(@ModelAttribute ProductForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getNewProductRegistration(form, model);
        }
        System.out.println(form);

        ProductForm productForm = new ProductForm();

        productForm.setProductId(form.getProductId()); // 商品ID
        productForm.setProductName(form.getProductName()); // 商品名
        productForm.setProductPrice(form.getProductPrice()); // 商品金額
        productForm.setProductType(form.getProductType()); // 商品の種類

        boolean result = productInformationServiceImpl.insertOne(productForm);

        //会員登録結果の判定
        if (result = true) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }

        return "base/login";
    }

    // 商品詳細画面のGETメソッド
    @GetMapping("/productDetail/{id:.+}")
    public String getProductDetail(@ModelAttribute ProductForm productForm, Model model, @PathVariable("id") String productId) {

        System.out.println("productId =" + productId);

        model.addAttribute("contents", "base/product/productDetail::productDetail_contents");

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

    // 更新用のpostメソッド
    @PostMapping(value = "/productDetail", params = "update")
    public String postProductDetailUpdate(@ModelAttribute ProductForm productForm, Model model) {
        System.out.println("更新ボタンの処理");

        boolean result = productInformationServiceImpl.updateOne(productForm);

        if (result) {
            model.addAttribute("result", "更新成功");
        } else {
            model.addAttribute("result", "更新失敗");
        }
        return getProductInformation(productForm, model);
    }

    // 商品情報削除画面のPOSTメソッド
    @PostMapping(value = "/productDetail", params = "delete")
    public String postProductDelete(@ModelAttribute ProductForm productForm, Model model) {

        System.out.println("削除ボタンの処理");

        boolean result = productInformationServiceImpl.deleteOne(productForm.getProductId());

        if (result) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }
        return getProductInformation(productForm, model);
    }

    // 商品一覧のCSV出力処理
    @GetMapping("/productList/csv")
    public ResponseEntity<byte[]> getProductListCsv(Model model) {

        // 商品を全件取得して、CSVをサーバーに保存する
        productInformationServiceImpl.productCsvOut();

        byte[] bytes = null;

        try {

            bytes = productInformationServiceImpl.getFile("productInformation.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "product.csv");

        // productInformation.csvを戻す
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
