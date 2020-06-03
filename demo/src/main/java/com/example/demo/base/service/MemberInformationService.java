package com.example.demo.base.service;

import com.example.demo.base.dao.memberInformation.MemberInformationDto;
import com.example.demo.base.domain.memberInformation.MemberForm;

import java.util.List;

public interface MemberInformationService {

    /**
     * 会員テーブに1件データを作製する.
     *
     * @param memberForm 作製データ
     * @return 作製データ
     */
    boolean insertOne(MemberForm memberForm);

    /**
     * 会員テーブルから1件データを取得する.
     *
     * @param userId 会員ID
     * @return 取得データ
     */
    MemberInformationDto selectOne(String userId);

    /**
     * 会員テーブルから全データを取得する.
     *
     * @return 取得データ
     */
    List<MemberInformationDto> selectMany();

    /**
     * 会員テーブルを1件更新する.
     *
     * @param memberInformationDto 更新するデータ
     * @return 更新の有無
     */
    boolean updateOne(MemberInformationDto memberInformationDto);

    /**
     * 会員テーブルから1件データを削除する.
     *
     * @param memberId 削除する会員ID
     * @return 削除の有無
     */
    boolean deleteOne(String memberId);
}
