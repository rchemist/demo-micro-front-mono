/*
 *  Copyright (c) 2022. rchemist.io by Rchemist
 *  Licensed under the Rchemist Common License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License under controlled by Rchemist
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.rchemist.common.security.auth.configuration;

import io.rchemist.common.security.auth.service.GlobalAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * <p>
 * Project : Rchemist Commerce Platform
 * <p>
 * Created by kunner
 **/
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class GlobalSecurityConfig extends WebSecurityConfigurerAdapter {

  protected final GlobalAuthenticationProvider globalAuthenticationProvider;

  @Override
  protected void configure(AuthenticationManagerBuilder builder) throws Exception {
    builder.authenticationProvider(globalAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/mypage/**").authenticated()    // 로그인해야 접근할 수 있는 URL 설정
        .antMatchers("/**").permitAll();


    http.formLogin().disable();   // 굳이 form login 을 사용할 필요 없음

    http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true);

    http.exceptionHandling()
        .accessDeniedPage("/error");

    log.info("Successfully override GlobalSecurityConfig.");

  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers(
            "/favicon.ico"
            ,"/error"
            , "/asset/**"
        );
  }


}
