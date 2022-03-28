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

package io.rchemist.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Role 기반 권한 체크 설정 예시
 *
 * Controller 에서 @Secured 를 사용할 때 이 설정을 활성화 합니다.
 * io.rchemist.customer.config.CustomerRoleHierarchy 와 같이 hierarchical Roles 을 설정할 수도 있습니다.
 *
 * <p>
 * Project : Rchemist Commerce Platform
 * <p>
 * Created by kunner
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class FrontRoleSecurityConfig extends GlobalMethodSecurityConfiguration {

}
