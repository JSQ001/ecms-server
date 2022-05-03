package com.eicas.security.config;

import com.eicas.security.handler.*;
import com.eicas.security.service.impl.AuthUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @author osnudt
 * @since 2022-04-19
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*
     * 登录成功处理逻辑
     */
    @Resource
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    /*
     * 登录失败处理逻辑
     */
    @Resource
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    /*
     * 权限拒绝处理逻辑
     */
    @Resource
    CustomizeAccessDeniedHandler accessDeniedHandler;

    /*
     * 匿名用户访问无权限资源时的异常
     */
    @Resource
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    /*
     * 会话失效(账号被挤下线)处理逻辑
     */
    @Resource
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    /*
     * 登出成功处理逻辑
     */
    @Resource
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    /*
     * 获取用户账号密码及权限信息
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthUserServiceImpl();
    }

    /*
     * 设置默认的加密方式（强hash方式加密）
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * 关闭跨站请求防护
         */
        http.cors().and().csrf().disable();
        /*
         * 登录处理
         */
        http.formLogin()
                .permitAll()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
        /*
         * 异常处理(权限拒绝、登录失效)
         */
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);

        /*
         * 登出处理
         */
        http.logout().permitAll().logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID");
        /*
         * 会话管理
         */
        http.sessionManagement().maximumSessions(3).expiredSessionStrategy(sessionInformationExpiredStrategy);
        /*
         * 过滤请求
         */
        http.authorizeRequests()
                .antMatchers("/login", "/register", "/logout").anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/profile/**"
                ).permitAll()
                .antMatchers("/swagger-ui.html").anonymous()
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/api-docs").anonymous()
                .antMatchers("/druid/**").anonymous()
                .anyRequest().authenticated();
    }
}
