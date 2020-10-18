package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // パスワードエンコーダーのBean定義
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // データソース
    @Autowired
    private DataSource dataSource;

    private static final String EMPLOYEE_SQL = "SELECT"
            + " EMPLOYEE_ID,"
            + " PASSWORD,"
            + " true"
            + " FROM"
            + " EMPLOYEE"
            + " WHERE"
            + " EMPLOYEE_ID = ?";

    private static final String EMPLOYEE_ROLE_SQL = "SELECT"
            + " EMPLOYEE_ID,"
            + " ROLE"
            + " FROM"
            + " EMPLOYEE"
            + " WHERE"
            + " EMPLOYEE_ID = ?";

    private static final String MEMBER_SQL = "SELECT"
            + " MEMBER_ID,"
            + " PASSWORD,"
            + " true"
            + " FROM"
            + " MEMBER"
            + " WHERE"
            + " MEMBER_ID = ?";

    private static final String MEMBER_ROLE_SQL = "SELECT"
            + " MEMBER_ID,"
            + " ROLE"
            + " FROM"
            + " MEMBER"
            + " WHERE"
            + " MEMBER_ID = ?";

    public void configure(WebSecurity web) throws Exception {

        // 静的リソースを除外
        // 静的リソースへのアクセスには、セキュリティを適用しない
        web.ignoring().antMatchers("/webjars/**", "/css/**");
    }

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/webjars/**").permitAll() // webjarsへのアクセス許可
                .antMatchers("/css/**").permitAll() // cssへアクセス許可
                .antMatchers("/login").permitAll() // ログインページは直リンクOK
                .antMatchers("/employeeInformation_contents").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated(); // それ以外は直リンク禁止

        // ログイン処理
        http.formLogin()
                .loginProcessingUrl("/home") // ログイン処理のパス
                .loginPage("/login") // ログインページの指定
                .failureUrl("/login") // ログイン失敗時の遷移先
                .usernameParameter("employeeId") // ログインページのユーザーID
                .passwordParameter("password") // ログインページのパスワード
                .defaultSuccessUrl("/home", true); // ログイン成功後の遷移先

        // ログアウト処理
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");

        // CSRF対策を無効に設定(一時的)
        http.csrf().disable();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // ログイン処理時DBから取得する
        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(EMPLOYEE_SQL)
                    .authoritiesByUsernameQuery(EMPLOYEE_ROLE_SQL)
                    .passwordEncoder(passwordEncoder());
        } finally {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(MEMBER_SQL)
                    .authoritiesByUsernameQuery(MEMBER_ROLE_SQL)
                    .passwordEncoder(passwordEncoder());
        }

    }
}
