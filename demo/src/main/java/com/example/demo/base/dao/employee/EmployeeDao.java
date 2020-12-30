package com.example.demo.base.dao.employee;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * employeeテーブルにアクセスするためのDataAccessObjectを表すインターフェース.
 */
@Mapper
public interface EmployeeDao {

    /**
     * 従業員テーブルから1件データを取得
     *
     * @param employeeId 従業員ID
     * @return 取得データ
     * @throws DataAccessException データ取得時にエラーが発生した場合に投げられる例外
     */
    EmployeeDto selectOne(String employeeId) throws DataAccessException;

    /**
     * 従業員テーブルから複数件データを取得
     *
     * @param employeeId   従業員ID
     * @param employeeName 従業員名
     * @return 取得データ
     * @throws DataAccessException データ取得時にエラーが発生した場合に投げられる例外
     */
    List<EmployeeDto> selectMany(String employeeId, String employeeName) throws DataAccessException;

    /**
     * 従業員テーブルに1件データを作成
     *
     * @param employeeDto 作製データ
     * @return データ作成件数
     * @throws DataAccessException データ作成時にエラーが発生した場合に投げられる例外
     */
    int insertOne(EmployeeDto employeeDto) throws DataAccessException;

    /**
     * 従業員テーブルから1件データを更新
     *
     * @param employeeDto 更新データ
     * @return データ更新件数
     * @throws DataAccessException データ更新時にエラーが発生した場合に投げられる例外
     */
    int updateOne(EmployeeDto employeeDto) throws DataAccessException;

    /**
     * 従業員テーブルから1件データを削除
     *
     * @param employeeId 削除データ会員ID
     * @return データ削除件数
     * @throws DataAccessException データ削除時にエラーが発生した場合に投げられる例外
     */
    int deleteOne(String employeeId) throws DataAccessException;
}
