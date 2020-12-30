package com.example.demo.base.dao.member;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * memberテーブルにアクセスするためのDataAccessObjectを表すインターフェース.
 */
@Mapper
public interface MemberDao {

    /**
     * 会員テーブルから1件データを取得
     *
     * @param userId 会員ID
     * @return 取得データ
     * @throws DataAccessException データ取得時にエラーが発生した場合に投げられる例外
     */
    MemberDto selectOne(String userId) throws DataAccessException;

    /**
     * 会員テーブルから会員が情報を取得
     *
     * @param memberId   会員ID
     * @param memberName 会員名
     * @return 取得データ
     * @throws DataAccessException データ取得時にエラーが発生した場合に投げられる例外
     */
    MemberDto selectOneForMember(String memberId, String memberName) throws DataAccessException;

    /**
     * 商品テーブルから複数件データを取得
     *
     * @param memberId   会員ID
     * @param memberName 会員名
     * @return 取得データ
     * @throws DataAccessException データ取得時にエラーが発生した場合に投げられる例外
     */
    List<MemberDto> selectMany(String memberId, String memberName) throws DataAccessException;

    /**
     * 会員テーブルに1件データを作成
     *
     * @param memberDto 作製データ
     * @return データ作成件数
     * @throws DataAccessException データ作成時にエラーが発生した場合に投げられる例外
     */
    int insertOne(MemberDto memberDto) throws DataAccessException;

    /**
     * 会員テーブルから1件データを更新
     *
     * @param memberDto 更新データ
     * @return データ更新件数
     * @throws DataAccessException データ更新時にエラーが発生した場合に投げられる例外
     */
    int updateOne(MemberDto memberDto) throws DataAccessException;

    /**
     * 会員テーブルから1件データを削除
     *
     * @param userId 削除データ会員ID
     * @return データ削除件数
     * @throws DataAccessException データ削除時にエラーが発生した場合に投げられる例外
     */
    int deleteOne(String userId) throws DataAccessException;
}
