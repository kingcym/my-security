package com.cym.security.app.jwt;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/7 12:34
 */
@Configuration
public class TokenStoreConfig {
    private static Logger logger = Logger.getLogger(TokenStoreConfig.class);

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Autowired
    private TokenEndpoint tokenEndpoint;

    @PostConstruct
    public void init() {
        Set<HttpMethod> allowedMethods =
                new HashSet<>(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
        tokenEndpoint.setAllowedRequestMethods(allowedMethods);
    }


    @Bean
    @ConditionalOnProperty(prefix = "cym.security.oauth2",name="storeType",havingValue = "redis")
    public TokenStore redisTokenStore(){
        logger.info("***************redisTokenStore*********************");
        return new RedisTokenStore(redisConnectionFactory);
    }


    @Configuration
    //matchIfMissing = true 找不到prefix，name也会生效
    @ConditionalOnProperty(prefix = "cym.security.oauth2",name="storeType",havingValue = "jwt",matchIfMissing = true)
    public static class JwtTpkenConfig{

        @Bean
        public TokenStore jwtTokenStore(){
            logger.info("***************jwtTokenStore*********************");
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /**
         * 对token生成处理
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
            //密钥
            accessTokenConverter.setSigningKey("kingcym");

            return accessTokenConverter;
        }


        /**
         * 对生成jwttoken添加自定义参数
         * @return
         */
        @Bean
        @ConditionalOnMissingBean(name = "jwtTokenEnhancer")
        public TokenEnhancer jwtTokenEnhancer(){
            return new JwtTokenEnhancer();
        }
    }

}
