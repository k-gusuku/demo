package com.example.demo.base.dao.memberhistory;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * memberHistoryテーブルにアクセスするためのDataAccessObjectを表すインターフェース.
 */
@Mapper
public interface MemberHistoryDao {

    /**
     * 会員履歴を取得
     *
     * @param memberId 会員ID
     * @return 取得データ
     * @throws DataAccessException データ取得時にエラーが発生した場合に投げられる例外
     */
    List<MemberHistoryDto> selectMemberHistory(String memberId) throws DataAccessException;

    /**
     * 会員履歴テーブルに1件デーータを作成
     *
     * @param memberHistoryDto 作成データ
     * @return データ作成件数
     * @throws DataAccessException データ作成時にエラーが発生した場合に投げられる例外
     */
    int insertOne(MemberHistoryDto memberHistoryDto) throws DataAccessException;
}
