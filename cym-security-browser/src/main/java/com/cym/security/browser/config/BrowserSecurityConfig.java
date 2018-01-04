package com.cym.security.browser.config;

import com.cym.security.browser.authentication.CymAuthenticationSuccessHandle;
import com.cym.security.browser.filter.sms.SmsCodeFilter;
import com.cym.security.browser.filter.validate.ValidateCodeFilter;
import com.cym.security.browser.properties.BrowserProperties;
import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;


/**
 * @Author: Kingcym
 * @Description:
 * @Date: 2017/12/27 23:14
 */
/**
 * spring Security配置
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = Logger.getLogger(this.getClass());

    private final String DEFAULHTML = "http://127.0.0.1:8888/login/index.html";
    private final String DEFAULTURL = "/authentication/require";

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BrowserProperties browserProperties;

    @Autowired
    private AuthenticationSuccessHandler cymAuthenticationSuccessHandle;

    @Autowired
    private AuthenticationFailureHandler cymAuthenticationFailHandle;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SpringSocialConfigurer cymSpringSocialConfigurer;
    /**
     * security安全验证加密
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //配置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        //创建表 （只能创建一次，否则报错）
 //       jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }





    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("==========安全验证BrowserSecurityConfig的configure方法======================");
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(cymAuthenticationFailHandle);
        validateCodeFilter.setBrowserProperties(browserProperties);
        validateCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(cymAuthenticationFailHandle);
        smsCodeFilter.setBrowserProperties(browserProperties);
        smsCodeFilter.afterPropertiesSet();
        http
                 //添加过滤器
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()    //表单验证
                    //跳转指定页面登陆
    //                .loginPage(DEFAULHTML)
                    //跳转指定接口
                    .loginPage(DEFAULTURL)
                    //用户验证路径
                    .loginProcessingUrl("/authentication/form")
                    //登陆成功，指定类处理
                    .successHandler(cymAuthenticationSuccessHandle)
                    //登陆失败，指定类处理
                    .failureHandler(cymAuthenticationFailHandle)
                    .and()
                //免登陆功能（记住我）
                .rememberMe()
                    .tokenRepository(persistentTokenRepository)
                    //单位秒
                    .tokenValiditySeconds(browserProperties.getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                    //前端传入参数
                    .rememberMeParameter("rememberMe")
                .and()
                .authorizeRequests()//授权
                .antMatchers(DEFAULTURL,
                        browserProperties.getLoginUrl(),
                        "/code/*",
                        "http://127.0.0.1:8888/login/logup.html",
                        "/user/regist"
                        ).permitAll() //DEFAULTURL这个路径不需要认证
                .anyRequest() //所有请求
                .authenticated()//都需要身份认
                .and()
                .csrf().disable() //csrf防护功能关闭
                .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                .apply(cymSpringSocialConfigurer);
        logger.info("==========安全验证BrowserSecurityConfig的configure方法====end==================");

    }




}
