package com.example.demo.base.service;

import com.example.demo.base.dao.memberHistory.MemberHistoryDto;

import java.util.List;

public interface MemberHistoryService {

    /**
     * 会員履歴テーブルからデータを取得する.
     *
     * @param memberId 会員ID
     * @return 取得データ.
     */
    List<MemberHistoryDto> selectMemberHistory(String memberId);

    /**
     * 会員履歴テーブルに1件データを作成する
     *
     * @param memberHistoryDto 作成データ
     * @return 作成データ
     */
    boolean insertOne(MemberHistoryDto memberHistoryDto);
}
