package com.cym.security.browser.social.qq.config;

import javax.sql.DataSource;

import com.cym.security.browser.properties.QQProperties;
import com.cym.security.browser.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;


/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2018/1/1 22:09
 */
@Configuration
@EnableSocial
@Order(1)
public class CymSocialConfig extends SocialConfigurerAdapter{
    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired(required = false)
    ConnectionSignUp connectionSignUp;

    @Autowired
    private QQProperties qqProperties;


    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
        jdbcUsersConnectionRepository.setTablePrefix("z_");
        if (connectionSignUp != null){
          //  jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
        }
        return jdbcUsersConnectionRepository;
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new QQConnectionFactory(qqProperties.getProviderId(),qqProperties.getAppId(),qqProperties.getAppSecret()));
    }
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public SpringSocialConfigurer cymSpringSocialConfigurer(){
        CymSpringSocialConfigurer cymSpringSocialConfigurer = new CymSpringSocialConfigurer("/qqLogin");
        //若第三方软件未与用户关联,跳转绑定地址
   //     cymSpringSocialConfigurer.signupUrl("http://127.0.0.1:8888/login/logup.html");//注册地址
        cymSpringSocialConfigurer.signupUrl("/register");//注册地址
        return cymSpringSocialConfigurer;
    }


    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator));
    }


//    @Bean
//    @Scope(
//            value = "request",
//           proxyMode = ScopedProxyMode.INTERFACES
//    )
//    public ConnectionRepository connectionRepository(UsersConnectionRepository usersConnectionRepository) {
//        ConnectionRepository jdbcConnectionRepository = usersConnectionRepository.createConnectionRepository(userIdSource.getUserId());
//        return jdbcConnectionRepository;
//    }

}
