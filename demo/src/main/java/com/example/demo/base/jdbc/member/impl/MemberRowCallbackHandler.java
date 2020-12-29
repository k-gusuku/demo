package com.example.demo.base.jdbc.member.impl;

import org.springframework.jdbc.core.RowCallbackHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowCallbackHandler implements RowCallbackHandler {

    @Override
    public void processRow(ResultSet rs) throws SQLException {
        try {

            File file = new File("member.csv");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            do {

                String str = rs.getString("MEMBER_ID") + ","
                        + rs.getString("MEMBER_NAME") + ","
                        + rs.getDate("BIRTHDAY") + ","
                        + rs.getInt("AGE") + ","
                        + rs.getInt("PHONE_NUMBER") + ","
                        + rs.getString("ADDRESS");

                bw.write(str);
                bw.newLine();

            } while (rs.next());

            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
