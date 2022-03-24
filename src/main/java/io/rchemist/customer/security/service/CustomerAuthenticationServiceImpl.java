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

package io.rchemist.customer.security.service;

import io.rchemist.common.extension.ExtensionResultStatusType;
import io.rchemist.common.security.auth.data.AuthenticationRequest;
import io.rchemist.common.security.auth.data.AuthenticationResponse;
import io.rchemist.common.security.service.SecurityHelper;
import io.rchemist.common.security.service.exception.AuthenticationErrorType;
import io.rchemist.common.security.service.exception.AuthenticationException;
import io.rchemist.common.security.service.exception.UserAuthenticationErrorType;
import io.rchemist.common.security.service.exception.UserAuthenticationException;
import io.rchemist.common.security.service.exception.UserServiceException;
import io.rchemist.common.util.type.SiteType;
import io.rchemist.customer.data.RegisterRequest;
import io.rchemist.customer.domain.Customer;
import io.rchemist.customer.service.CustomerLoginTraceService;
import io.rchemist.customer.service.CustomerService;
import io.rchemist.endpoint.tenant.TenantClientService;
import io.rchemist.tenant.data.TenantDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Project : Rchemist Commerce Platform
 * <p>
 * Created by kunner
 **/
@RequiredArgsConstructor
@Service("rcmCustomerAuthenticationService")
public class CustomerAuthenticationServiceImpl implements CustomerAuthenticationService {

  protected final CustomerService customerService;

  protected final TenantClientService tenantClientService;

  protected final CustomerLoginTraceService customerLoginTraceService;

  /**
   * 비밀번호 체크를 하지 않고 회원 정보가 존재하면 무조건 로그인 시키는 로직을 사용하려면 이 값을 true 로 셋팅한다.
   */
  @Value("${platform.config.customer.simple-authentication:false}")
  protected boolean simpleAuthentication;

  @Override
  @Transactional
  public AuthenticationResponse requestSignin(
      AuthenticationRequest request) throws AuthenticationException {

    validateRequest(request.getTenantAlias(), request.getSiteAlias());

    Customer customer = customerService.findCustomerByUsername(request.getUsername());

    boolean failure = isSigninFailure(request, customer);

    if(failure)
      throw new AuthenticationException(AuthenticationErrorType.USER_NOT_FOUND);

    AuthenticationResponse response = getAuthenticationResponse(customer);

    return response;
  }

  /**
   * SimpleLogin 모드에서 비밀번호 체크를 하지 않는 기능을 추가한다.
   * @param request
   * @param customer
   * @return
   */
  private boolean isSigninFailure(AuthenticationRequest request, Customer customer) {
    if(customer == null)
      return true;

    if(simpleAuthentication){
      return false;
    }else{
      return !SecurityHelper.equalPassword(request.getPassword(), customer.getRecentPassword());
    }
  }

  private void validateRequest(String tenantAlias, String siteAlias) throws AuthenticationException {
    // tenant 에 대한 캐싱 정보를 가져야 한다.
    // 단, 이 캐싱 정보는 tenant 정보가 변경될 때 알아서 refresh 되어야 한다.
    // 이 로직은 반드시 각 서비스에 들어가야 한다. tenantClientService 에서는 처리하지 않는다.
    TenantDTO tenant = tenantClientService.findTenantByAlias(tenantAlias);

    if(tenant == null)
      throw new AuthenticationException(AuthenticationErrorType.TENANT_NOT_FOUND);

    boolean siteExist = CollectionUtils.emptyIfNull(tenant.getSites()).stream()
        .anyMatch(site -> StringUtils.equals(siteAlias, site.getAlias()));

    if(!siteExist)
      throw new AuthenticationException(AuthenticationErrorType.SITE_NOT_FOUND);
  }

  protected AuthenticationResponse getAuthenticationResponse(Customer customer) {
    AuthenticationResponse response = new AuthenticationResponse();
    response.setUserId(customer.getId());
    response.setSiteType(SiteType.FRONT.getType());
    response.setUsername(customer.getUsername());
    response.setPassword(customer.getRecentPassword());
    response.setTenantAlias(customer.getTenantAlias());
    response.setSiteAlias(customer.getSiteAlias());
    response.setRoles(customer.getUserPermissions());

    return response;
  }

  @Override
  @Transactional
  public AuthenticationResponse requestRegister(RegisterRequest request)
      throws UserAuthenticationException, UserServiceException, AuthenticationException {

    validateRequest(request.getTenantAlias(), request.getSiteAlias());

    // #1. username duplicate 확인
    Customer customer = customerService.findCustomerByUsername(request.getUsername());
    if(customer != null)
      throw new UserAuthenticationException(UserAuthenticationErrorType.DUPLICATED_USERNAME);

    // #2. emailAddress duplicate 확인
    customer = customerService.findCustomerByEmailAddress(request.getEmailAddress());
    if(customer != null)
      throw new UserAuthenticationException(UserAuthenticationErrorType.DUPLICATED_EMAILADDRESS);

    // #3. password valid 확인
    if(!SecurityHelper.validPassword(request.getPassword()))
      throw new UserAuthenticationException(UserAuthenticationErrorType.INVALID_PASSWORD);

    // #4. 기본 정보 셋팅, User 엔티티 생성 후 리턴
    customer = Customer.register(request);

    customer = customerService.save(customer);

    return getAuthenticationResponse(customer);
  }

  @Override
  public ExtensionResultStatusType traceLoginResult(String userId, boolean error) {

    Customer customer = customerService.findCustomerById(userId);

    if(customer == null)
      return ExtensionResultStatusType.NOT_HANDLED;

    customerLoginTraceService.loginTrace(customer.getId(), !error);

    return ExtensionResultStatusType.HANDLED;
  }

}
