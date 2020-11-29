package com.example.demo.base.dao.employeeinformation;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface EmployeeInformationDao {

    /**
     * 従業員テーブルから1件データを取得.
     *
     * @param employeeId 従業員ID
     * @return 取得データ
     * @throws DataAccessException データ取得時に投げられるエラー
     */
    EmployeeInformationDto selectOne(String employeeId) throws DataAccessException;

    /**
     * 従業員テーブルから複数件データを取得.
     *
     * @param employeeId   従業員ID
     * @param employeeName 従業員名
     * @return 取得データ
     * @throws DataAccessException データ取得時に投げられるエラー
     */
    List<EmployeeInformationDto> selectMany(String employeeId, String employeeName) throws DataAccessException;

    /**
     * 従業員テーブルに1件データを作成.
     *
     * @param employeeInformationDto 作製データ
     * @return データ作成件数
     * @throws DataAccessException データ作成時に投げられるエラー
     */
    int insertOne(EmployeeInformationDto employeeInformationDto) throws DataAccessException;

    /**
     * 従業員テーブルから1件データを更新.
     *
     * @param employeeInformationDto 更新データ
     * @return データ更新件数
     * @throws DataAccessException データ更新時に投げられるエラー
     */
    int updateOne(EmployeeInformationDto employeeInformationDto) throws DataAccessException;

    /**
     * 従業員テーブルから1件データを削除.
     *
     * @param employeeId 削除データ会員ID
     * @return データ削除件数
     * @throws DataAccessException データ削除時に投げられるエラー
     */
    int deleteOne(String employeeId) throws DataAccessException;

    /**
     * SQL取得結果をサーバーにCSVで保存.
     *
     * @throws DataAccessException CSV保存時に投げられるエラー
     */
    void employeeCsvOut() throws DataAccessException;
}
