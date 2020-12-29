package com.example.demo.base.controller;

import com.example.demo.base.conversion.employee.EmployeeConversion;
import com.example.demo.base.dao.employee.EmployeeDto;
import com.example.demo.base.domain.employee.EmployeeForm;
import com.example.demo.base.domain.employee.EmployeeGroupOrder;
import com.example.demo.base.service.EmployeeService;
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

@Controller
public class EmployeeController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 未入力のStringをnullに設定する
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private final EmployeeService employeeService;
    private final EmployeeConversion employeeConversion;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeConversion employeeConversion) {
        this.employeeService = employeeService;
        this.employeeConversion = employeeConversion;
    }

    // 従業員情報のGET用メソッド
    @GetMapping("/employee_contents")
    public String getEmployee(@ModelAttribute EmployeeForm form, Model model) {
        List<EmployeeDto> employeeDtoList = employeeService.selectMany(form.getEmployeeId(), form.getEmployeeName());

        model.addAttribute("contents", "base/employee/employee::employee_contents");
        model.addAttribute("employeeDtoList", employeeDtoList);

        return "base/homeLayout";
    }

    // 従業員詳細画面のGETメソッド
    @GetMapping("/employeeDetail/{id:.+}")
    public String getEmployeeDetail(@ModelAttribute EmployeeForm employeeForm, Model model, @PathVariable("id") String employeeId) {
        System.out.println("employeeId =" + employeeId);

        if (employeeId != null && employeeId.length() > 0) {
            employeeForm = employeeConversion.dto2Form(employeeService.selectOne(employeeId));

            model.addAttribute("contents", "base/employee/employeeDetail::employeeDetail_contents");
            model.addAttribute("employeeForm", employeeForm);
        }
        return "base/homeLayout";
    }

    // 新規従業員登録のGETメソッド
    @GetMapping("/newEmployeeRegistration_contents")
    public String getNewEmployeeRegistration(@ModelAttribute EmployeeForm form, Model model) {
        model.addAttribute("contents", "base/employee/newEmployeeRegistration::newEmployeeRegistration_contents");
        return "base/homeLayout";
    }

    // 新規従業員登録のPOSTメソッド
    @PostMapping("/newEmployeeRegistration_contents")
    public String postNewEmployeeRegistration(@ModelAttribute @Validated(EmployeeGroupOrder.class) EmployeeForm employeeForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getNewEmployeeRegistration(employeeForm, model);
        }
        System.out.println(employeeForm);

        employeeForm.setRole("ROLE_ADMIN"); // 権限
        boolean result = employeeService.insertOne(employeeConversion.form2Dto(employeeForm));

        // 会員登録結果の判定
        if (result) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }

        // 従業員情報画面の検索結果用に従業員IDと従業員名を空にする。
        employeeForm.setEmployeeId(null);
        employeeForm.setEmployeeName(null);

        return getEmployee(employeeForm, model);
    }

    // 更新用のpostメソッド
    @PostMapping(value = "/employeeDetail", params = "update")
    public String postEmployeeDetailUpdate(@ModelAttribute @Validated(EmployeeGroupOrder.class) EmployeeForm employeeForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() && bindingResult.getErrorCount() > 1) {
            model.addAttribute("contents", "base/employee/employeeDetail::employeeDetail_contents");
            return "base/homeLayout";
        }
        System.out.println("更新ボタンの処理");

        boolean result = employeeService.updateOne(employeeConversion.form2Dto(employeeForm));

        if (result) {
            model.addAttribute("result", "更新成功");
        } else {
            model.addAttribute("result", "更新失敗");
        }

        return getEmployeeDetail(employeeForm, model, employeeForm.getEmployeeId());
    }

    // 従業員情報削除画面のPOSTメソッド
    @PostMapping(value = "/employeeDetail", params = "delete")
    public String postEmployeeDelete(@ModelAttribute EmployeeForm employeeForm, Model model) {
        System.out.println("削除ボタンの処理");

        boolean result = employeeService.deleteOne(employeeForm.getEmployeeId());

        if (result) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }

        // 従業員情報画面の検索結果用に従業員IDと従業員名を空にする。
        employeeForm.setEmployeeId("");
        employeeForm.setEmployeeName("");

        return getEmployee(employeeForm, model);
    }

    // 従業員一覧のCSV出力処理
    @GetMapping("/employeeList/csv")
    public ResponseEntity<byte[]> getEmployeeListCsv(Model model) {
        // 会員を全件取得して、CSVをサーバーに保存する
        employeeService.employeeCsvOut();

        byte[] bytes = null;

        try {

            bytes = employeeService.getFile("employee.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "employee.csv");

        // member.csvを戻す
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
