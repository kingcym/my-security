/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cym.security.app.granter;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * @author Dave Syer
 * 
 */
public class ResourceOwnerSMSTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "sms";

	private final AuthenticationProvider authenticationProvider;

	public ResourceOwnerSMSTokenGranter(AuthenticationProvider authenticationProvider,
										AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
		this(authenticationProvider, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
	}

	protected ResourceOwnerSMSTokenGranter(AuthenticationProvider authenticationProvider, AuthorizationServerTokenServices tokenServices,
										   ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
		super(tokenServices, clientDetailsService, requestFactory, grantType);
		this.authenticationProvider = authenticationProvider;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

		Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
		String mobile = parameters.get("mobile");

		Authentication userAuth = new SmsCodeAuthenticationToken(mobile);
		((AbstractAuthenticationToken) userAuth).setDetails(parameters);
		try {
			userAuth = authenticationProvider.authenticate(userAuth);
		}
		catch (AccountStatusException ase) {
			//covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
			throw new InvalidGrantException(ase.getMessage());
		}
		catch (BadCredentialsException e) {
			// If the username/password are wrong the spec says we should send 400/invalid grant
			throw new InvalidGrantException(e.getMessage());
		}
		if (userAuth == null || !userAuth.isAuthenticated()) {
			throw new InvalidGrantException("Could not authenticate user: " + mobile);
		}
		
		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);		
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}
}
