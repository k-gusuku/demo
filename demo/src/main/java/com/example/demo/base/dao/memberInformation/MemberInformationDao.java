package com.example.demo.base.dao.memberInformation;

import java.util.List;

import com.example.demo.base.domain.memberInformation.MemberForm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

@Mapper
public interface MemberInformationDao {

    /**
     * 会員テーブルに1件データを作成.
     *
     * @param memberForm 作製データ
     * @return データ作成件数
     * @throws DataAccessException データ作成時に投げられるエラー
     */
    int insertOne(MemberForm memberForm) throws DataAccessException;

    /**
     * 会員テーブルから1件データを取得.
     *
     * @param userId 会員ID
     * @return 取得データ
     * @throws DataAccessException データ取得時に投げられるエラー
     */
    MemberInformationDto selectOne(String userId) throws DataAccessException;

    /**
     * 会員テーブルから全件データを取得.
     *
     * @return 取得データ
     * @throws DataAccessException データ取得時に投げられるエラー
     */
    //memberテーブルの全データを取得
    List<MemberInformationDto> selectMany() throws DataAccessException;

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
}
