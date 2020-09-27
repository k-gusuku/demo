package com.example.demo.base.controller;

import com.example.demo.base.dao.employeeInformation.EmployeeInformationDto;
import com.example.demo.base.domain.employee.EmployeeForm;
import com.example.demo.base.service.impl.EmployeeInformationServiceImpl;
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
public class EmployeeInformationController {

    @Autowired
    EmployeeInformationServiceImpl employeeInformationServiceImpl;

    //従業員情報のGET用メソッド
    @GetMapping("/employeeInformation_contents")
    public String getEmployeeInformation(@ModelAttribute EmployeeForm form, Model model) {

        model.addAttribute("contents", "base/employee/employeeInformation::employeeInformation_contents");

        List<EmployeeInformationDto> employeeInformationDtoList = employeeInformationServiceImpl.selectMany(form.getEmployeeId(), form.getEmployeeName());

        model.addAttribute("employeeInformationDtoList", employeeInformationDtoList);

        return "base/homeLayout";
    }

    //従業員詳細画面のGETメソッド
    @GetMapping("/employeeDetail/{id:.+}")
    public String getEmployeeDetail(@ModelAttribute EmployeeForm employeeForm, Model model, @PathVariable("id") String employeeId) {

        System.out.println("employeeId =" + employeeId);

        model.addAttribute("contents", "base/employee/employeeDetail::employeeDetail_contents");

        if (employeeId != null && employeeId.length() > 0) {

            EmployeeInformationDto employeeInformationDto = employeeInformationServiceImpl.selectOne(employeeId);

            employeeForm.setEmployeeId(employeeInformationDto.getEmployeeId()); //従業員ID
            employeeForm.setEmployeeName(employeeInformationDto.getEmployeeName()); //従業員名

            model.addAttribute("employeeForm", employeeForm);
        }
        return "base/homeLayout";
    }

    //更新用のpostメソッド
    @PostMapping(value = "/employeeDetail", params = "update")
    public String postEmployeeDetailUpdate(@ModelAttribute EmployeeForm employeeForm, Model model) {
        System.out.println("更新ボタンの処理");

        boolean result = employeeInformationServiceImpl.updateOne(employeeForm);

        if (result) {
            model.addAttribute("result", "更新成功");
        } else {
            model.addAttribute("result", "更新失敗");
        }
        return getEmployeeInformation(employeeForm, model);
    }

    //従業員情報削除画面のPOSTメソッド
    @PostMapping(value = "/employeeDetail", params = "delete")
    public String postEmployeeDelete(@ModelAttribute EmployeeForm employeeForm, Model model) {

        System.out.println("削除ボタンの処理");

        boolean result = employeeInformationServiceImpl.deleteOne(employeeForm.getEmployeeId());

        if (result) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }
        return getEmployeeInformation(employeeForm, model);
    }

    //新規従業員登録のGETメソッド
    @GetMapping("/newEmployeeRegistration_contents")
    public String getNewEmployeeRegistration(@ModelAttribute EmployeeForm form, Model model) {
        model.addAttribute("contents", "base/employee/newEmployeeRegistration::newEmployeeRegistration_contents");
        return "base/homeLayout";
    }

    //新規従業員登録のPOSTメソッド
    @PostMapping("/newEmployeeRegistration_contents")
    public String postNewEmployeeRegistration(@ModelAttribute EmployeeForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getNewEmployeeRegistration(form, model);
        }
        System.out.println(form);

        EmployeeForm employeeForm = new EmployeeForm();

        employeeForm.setEmployeeId(form.getEmployeeId()); //従業員ID
        employeeForm.setEmployeeName(form.getEmployeeName()); //従業員名

        boolean result = employeeInformationServiceImpl.insertOne(employeeForm);

        //会員登録結果の判定
        if (result = true) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }

        return "base/login";
    }

    // 従業員一覧のCSV出力処理
    @GetMapping("/employeeList/csv")
    public ResponseEntity<byte[]> getEmployeeListCsv(Model model) {

        // 会員を全件取得して、CSVをサーバーに保存する
        employeeInformationServiceImpl.employeeCsvOut();

        byte[] bytes = null;

        try {

            bytes = employeeInformationServiceImpl.getFile("employeeInformation.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "employee.csv");

        // memberInformation.csvを戻す
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
