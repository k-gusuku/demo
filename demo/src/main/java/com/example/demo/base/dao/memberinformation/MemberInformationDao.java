package com.example.demo.base.dao.memberinformation;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface MemberInformationDao {

    /**
     * 会員テーブルから1件データを取得.
     *
     * @param userId 会員ID
     * @return 取得データ
     * @throws DataAccessException データ取得時に投げられるエラー
     */
    MemberInformationDto selectOne(String userId) throws DataAccessException;

    /**
     * 会員テーブルから会員が情報を取得.
     *
     * @param memberId 会員ID
     * @param memberName 会員名
     * @return 取得データ
     * @throws DataAccessException データ取得時に投げられるエラー
     */
    MemberInformationDto selectMember(String memberId, String memberName) throws DataAccessException;

    /**
     * 商品テーブルから複数件データを取得.
     *
     * @param memberId 会員ID
     * @param memberName 会員名
     * @return 取得データ
     * @throws DataAccessException データ取得時に投げられるエラー
     */
    List<MemberInformationDto> selectMany(String memberId, String memberName) throws DataAccessException;

    /**
     * 会員テーブルに1件データを作成.
     *
     * @param memberInformationDto 作製データ
     * @return データ作成件数
     * @throws DataAccessException データ作成時に投げられるエラー
     */
    int insertOne(MemberInformationDto memberInformationDto) throws DataAccessException;

    /**
     * 会員テーブルから1件データを更新.
     *
     * @param memberInformationDto 更新データ
     * @return データ更新件数
     * @throws DataAccessException データ更新時に投げられるエラー
     */
    int updateOne(MemberInformationDto memberInformationDto) throws DataAccessException;

    /**
     * 会員テーブルから1件データを削除.
     *
     * @param userId 削除データ会員ID
     * @return データ削除件数
     * @throws DataAccessException データ削除時に投げられるエラー
     */
    int deleteOne(String userId) throws DataAccessException;

    /**
     * SQL取得結果をサーバーにCSVで保存.
     *
     * @throws DataAccessException CSV保存時に投げられるエラー
     */
    void memberCsvOut() throws DataAccessException;
}
