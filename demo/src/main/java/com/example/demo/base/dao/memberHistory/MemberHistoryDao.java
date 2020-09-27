package com.example.demo.base.dao.memberHistory;

import com.example.demo.base.domain.memberHistory.MemberHistoryForm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface MemberHistoryDao {

    /**
     * 会員履歴を取得.
     *
     * @param memberId 会員ID
     * @return 取得データ
     * @throws DataAccessException データ取得時に投げられるエラー.
     */
    List<MemberHistoryDto> selectMemberHistory(String memberId) throws DataAccessException;

    /**
     * 会員履歴テーブルに1件デーータを作成.
     *
     * @param memberHistoryForm 作成データ
     * @return データ作成件数
     * @throws DataAccessException データ作成時に投げられるエラー.
     */
    int insertOne(MemberHistoryForm memberHistoryForm) throws DataAccessException;
}
