package com.example.demo.base.service;

import com.example.demo.base.dao.employee.EmployeeDto;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * Employeeのビジネスロジックを提供するモジュールを表すインターフェース.
 */
public interface EmployeeService {

    /**
     * 従業員テーブルから1件データを取得する.
     *
     * @param employeeId 従業員ID
     * @return 取得データ
     */
    EmployeeDto selectOne(String employeeId);

    /**
     * 従業員テーブルからデータを取得する.
     *
     * @param employeeId 商品ID
     * @param employeeName 商品名
     * @return 取得データ
     */
    List<EmployeeDto> selectMany(String employeeId, String employeeName);

    /**
     * 従業員テーブルを1件更新する.
     *
     * @param employeeDto 更新するデータ
     * @return 更新の有無
     */
    boolean updateOne(EmployeeDto employeeDto);

    /**
     * 従業員テーブに1件データを作製する.
     *
     * @param employeeDto 作製データ
     * @return 作製データ
     */
    boolean insertOne(EmployeeDto employeeDto);

    /**
     * 従業員テーブルから1件データを削除する.
     *
     * @param employeeId 削除する会員ID
     * @return 削除の有無
     */
    boolean deleteOne(String employeeId);

    /**
     * 従業員情報一覧をCSV出力する.
     *
     * @throws IOException 従業員情報一覧取得時に投げられるエラー.
     */
    void employeeCsvOut() throws DataAccessException;

    /**
     * サーバーに保存されているファイルを取得して、byte配列に変換する.
     *
     * @param fileName
     * @return byte配列に変換されたデータ
     * @throws IOException byte配列への変換時に投げられるエラー.
     */
    byte[] getFile(String fileName) throws IOException;
}
