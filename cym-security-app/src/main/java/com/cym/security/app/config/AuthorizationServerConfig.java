package com.cym.security.app.config;

import com.cym.security.app.exception.CymOauth2Exception;
import com.cym.security.app.filter.SmsCodeFilter;
import com.cym.security.app.granter.open.qq.OpenCodeAuthenticationProvider;
import com.cym.security.app.granter.open.qq.ResourceOwnerOpenTokenGranter;
import com.cym.security.app.granter.sms.ResourceOwnerSMSTokenGranter;
import com.cym.security.app.granter.sms.SmsCodeAuthenticationProvider;
import com.cym.security.app.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.*;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/6 18:07
 */
@Configuration
@EnableAuthorizationServer  //认证服务
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private TokenStore redisTokenStore;

    @Autowired(required = false)
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationFailureHandler cymAuthenticationFailHandle;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;



    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints          //.tokenStore(redisTokenStore) //将生成token存入redis
                .authenticationManager(authenticationManager)
                .userDetailsService(myUserDetailsService)
                .setClientDetailsService(clientDetailsService);
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer!=null) {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            ArrayList<TokenEnhancer> tokenEnhancers = new ArrayList<>();
            tokenEnhancers.add(jwtTokenEnhancer);
            tokenEnhancers.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

            endpoints
                    .tokenEnhancer(tokenEnhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter);
        }
        //添加自定义的tokenGranter  参考AuthorizationServerEndpointsConfigurer
        endpoints.tokenGranter(tokenGranter(endpoints.getTokenServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory(), endpoints.getAuthorizationCodeServices()));
        //添加自定义的异常
        endpoints.exceptionTranslator(new CymOauth2Exception());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        SmsCodeFilter smsCodeFilter = new SmsCodeFilter(redisTemplate, cymAuthenticationFailHandle);
        //添加可以拦截TokenEndpoint的拦截器
        security.addTokenEndpointAuthenticationFilter(smsCodeFilter);
        security.tokenKeyAccess("isAuthenticated()");

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("kingcym")
                .secret("kingcymsecret")
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(2592000) //一个月
                .authorizedGrantTypes("implicit", "refresh_token", "password", "authorization_code", "sms", "open")//授权模式
                .scopes("all", "read", "write")
                .autoApprove(true)  //自动授权，不用点确认
                .and()
                .withClient("kingcym2")
                .secret("kingcymsecret2")
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(2592000) //一个月
                .authorizedGrantTypes("implicit", "refresh_token", "password", "authorization_code", "sms")//授权模式
                .scopes("all", "read", "write")
                .autoApprove(true); //自动授权，不用点确认;
    }


    private SmsCodeAuthenticationProvider smsCodeAuthenticationProvider(UserDetailsService userDetailsService) {
        return new SmsCodeAuthenticationProvider(userDetailsService);
    }

    public TokenGranter tokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetails, OAuth2RequestFactory requestFactory, AuthorizationCodeServices authorizationCodeServices) {

        List<TokenGranter> tokenGranters = new ArrayList<>();
        /**
         * 默认的5个
         */
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices,
                clientDetails, requestFactory));
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory));
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));
        tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetails, requestFactory));
        /**
         * 自定义的
         */
        tokenGranters.add(new ResourceOwnerSMSTokenGranter(smsCodeAuthenticationProvider(myUserDetailsService), tokenServices, clientDetails, requestFactory));

        tokenGranters.add(new ResourceOwnerOpenTokenGranter(openCodeAuthenticationProvider(myUserDetailsService), tokenServices, clientDetails, requestFactory));

        return new CompositeTokenGranter(tokenGranters);
    }

    private AuthenticationProvider openCodeAuthenticationProvider(SocialUserDetailsService myUserDetailsService) {
        return new OpenCodeAuthenticationProvider(myUserDetailsService);
    }

    /**
     * security安全验证加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
