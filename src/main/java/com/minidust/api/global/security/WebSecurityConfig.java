package com.minidust.api.global.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable(); // TODO 왜 얘네를 지우면 POST 옵션에서 403 오류가 나올까?
        http.
                authorizeRequests()
                .antMatchers("/api/**")
                .permitAll()
                .antMatchers("/h2-console/**")
                .permitAll()
                .antMatchers("/**")
                .denyAll();
    }
}
