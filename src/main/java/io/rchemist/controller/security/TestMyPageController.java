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

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 마이페이지 접근 시 로그인한 유저만 가능하도록 설정이 되어 있는지 테스트하는 컨트롤러
 * GlobalSecurityConfig 정보에서 /mypage/** URL 을 authenticated 설정했으므로, 이 컨트롤러는 로그인한 경우에만 접근 가능
 *
 * /security-test/login-test 를 이용해 로그인 한 후 접근했을 때 에러가 나지 않으면 성공
 *
 * <p>
 * Project : Rchemist Commerce Platform
 * <p>
 * Created by kunner
 **/
@RequestMapping("/mypage")
@RestController
@Secured("ROLE_USER")
public class TestMyPageController {

  @RequestMapping
  public String viewMyPage(){
    return "WELCOME to MYPAGE :: LOGGED IN USER";
  }

}
