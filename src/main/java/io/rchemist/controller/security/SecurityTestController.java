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

package io.rchemist.controller.security;

import io.rchemist.common.security.auth.data.PlatformAuthenticationToken;
import io.rchemist.common.security.auth.data.SimpleAuthentication;
import io.rchemist.common.security.auth.data.Token;
import io.rchemist.common.security.auth.service.GatewayTokenAuthenticationService;
import io.rchemist.common.tenant.TenantHelper;
import io.rchemist.common.util.StringUtil;
import io.rchemist.common.util.type.SiteType;
import io.rchemist.common.web.request.resolver.SecurityContextResolver;
import io.rchemist.common.web.response.ResponseData;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 *
 * <p>
 * Project : Rchemist Commerce Platform
 * <p>
 * Created by kunner
 **/
@RequiredArgsConstructor
@RequestMapping("/security-test")
@RestController
public class SecurityTestController {

  private final GatewayTokenAuthenticationService gatewayTokenAuthenticationService;

  /**
   * 로그인 폼 대체 -> UI 에서 아이디/비밀번호를 입력하고 form 을 submit 하면 이 메소드로 전송
   * @param userId
   * @param password
   * @return
   */
  @GetMapping("/login-test")
  public String loginTest(@RequestParam String userId, @RequestParam String password){
    PlatformAuthenticationToken tokenRequest = new PlatformAuthenticationToken(userId, password
        , new ArrayList<>(), SiteType.FRONT.getType(), null, TenantHelper.getTenantAliasFromWebRequestContext()
        , TenantHelper.getSiteAliasFromWebRequestContext());

    ResponseData<Token> result = gatewayTokenAuthenticationService.createAccessToken(tokenRequest);

    if(result.isError()){
      return result.getError().getMessage();
    }else{
      // 이미 로그인 처리가 된 다음이므로 SecurityContextHolder 에 세션 정보가 저장되어 있음.
      // /open-login, /current 를 이용해 로그인 세션이 있는지 확인한다.
      return StringUtil.toJson(result.getData());
    }

  }

  /**
   * 로그인 여부와 관계 없이 보이는 화면 테스트
   * @return
   */
  @GetMapping("/open-all")
  public String getPreLoginTest(){
    return "OPEN URI FOR ALL USERS";
  }

  /**
   * 로그인 한 경우에만 접근 가능하도록 @Secured 테스트
   * (GlobalSecurityConfig 에서 authorized Url 로 설정하지 않아도 메소드 또는 컨트롤러 단위에서 접근 제어할 수 있음)
   *
   * @return
   */
  @GetMapping("/open-login")
  @Secured("ROLE_USER")
  public String getLoginUserOnly(){
    return "LOGIN USER ONLY";
  }

  /**
   * 현재 로그인된 세션 정보 확인
   *
   * 로그인하지 않은 경우 AnonymousAuthentication 정보가 리턴됨
   * @return
   */
  @GetMapping("/current")
  public String getCurrentLogin(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication == null){
      return "NOT LOGGED IN";
    }else{
      return "CURRENT LOGGED IN<br>"
          + StringUtil.toJson(authentication);
    }
  }

  /**
   * SimpleAuthentication 정보 리턴
   *
   * 어플리케이션 개발에 필요한 tenantAlias, siteAlias, token 등의 정보가 모두 저장되어 있음.
   * 로그인하지 않으면 AnonymousAuthentication 정보 리턴
   *
   * @return
   */
  @GetMapping("/current-helper")
  public String getCurrentLoginByHelper(){
    SimpleAuthentication authentication = SecurityContextResolver.getAuthentication();
    return "CURRENT USER IS " +
        authentication.toString();
  }

}
