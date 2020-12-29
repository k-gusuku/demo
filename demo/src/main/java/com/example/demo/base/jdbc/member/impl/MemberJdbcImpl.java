package com.example.demo.base.jdbc.member.impl;

import com.example.demo.base.jdbc.member.MemberJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MemberJdbcImpl implements MemberJdbc {

    private final JdbcTemplate jdbc;

    @Autowired
    public MemberJdbcImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void memberCsvOut() throws DataAccessException {
        String sql = "SELECT * FROM MEMBER";

        MemberRowCallbackHandler handler = new MemberRowCallbackHandler();

        jdbc.query(sql, handler);
    }
}
