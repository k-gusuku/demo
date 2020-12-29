package com.example.demo.base.jdbc.member;

import org.springframework.dao.DataAccessException;

public interface MemberJdbc {
    /**
     * SQL取得結果をサーバーにCSVで保存.
     *
     * @throws DataAccessException CSV保存時に投げられるエラー
     */
    void memberCsvOut() throws DataAccessException;
}
