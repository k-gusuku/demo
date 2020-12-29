package com.example.demo.base.service;

import com.example.demo.base.dao.member.MemberDto;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

public interface MemberService {

    /**
     * 会員テーブルから1件データを取得する.
     *
     * @param userId 会員ID
     * @return 取得データ
     */
    MemberDto selectOne(String userId);

    /**
     * 会員テーブルから会員が情報を取得.
     *
     * @param memberId 会員ID
     * @param memberName 会員名
     * @return 取得データ
     */
    MemberDto selectOneForMember(String memberId, String memberName);

    /**
     * 会員テーブルから全データを取得する.
     *
     * @param memberId 会員ID
     * @param memberName 会員名
     * @return 取得データ
     */
    List<MemberDto> selectMany(String memberId, String memberName);

    /**
     * 会員テーブに1件データを作製する.
     *
     * @param memberDto 作製データ
     * @return 作製データ
     */
    boolean insertOne(MemberDto memberDto);

    /**
     * 会員テーブルを1件更新する.
     *
     * @param memberDto 更新するデータ
     * @return 更新の有無
     */
    boolean updateOne(MemberDto memberDto);

    /**
     * 会員テーブルから1件データを削除する.
     *
     * @param memberId 削除する会員ID
     * @return 削除の有無
     */
    boolean deleteOne(String memberId);

    /**
     * 会員情報一覧をCSV出力する.
     *
     * @throws IOException 会員情報一覧取得時に投げられるエラー.
     */
    void memberCsvOut() throws DataAccessException;

    /**
     * サーバーに保存されているファイルを取得して、byte配列に変換する.
     *
     * @param fileName
     * @return byte配列に変換されたデータ
     * @throws IOException byte配列への変換時に投げられるエラー.
     */
    byte[] getFile(String fileName) throws IOException;
}
