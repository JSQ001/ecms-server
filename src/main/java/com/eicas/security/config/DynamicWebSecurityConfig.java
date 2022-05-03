//package com.eicas.security.config;
//
//import com.eicas.security.handler.*;
//import com.eicas.security.service.impl.AuthUserServiceImpl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import javax.annotation.Resource;
//
///**
// * @author osnudt
// * @since 2022-04-19
// */
//@EnableWebSecurity
//@Configuration
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,
//        securedEnabled = true,
//        jsr250Enabled = true)
//public class DynamicWebSecurityConfig extends WebSecurityConfigurerAdapter {
//    /*
//     * 登录成功处理逻辑
//     */
//    @Resource
//    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;
//
//    /*
//     * 登录失败处理逻辑
//     */
//    @Resource
//    CustomizeAuthenticationFailureHandler authenticationFailureHandler;
//
//    /*
//     * 权限拒绝处理逻辑
//     */
//    @Resource
//    CustomizeAccessDeniedHandler accessDeniedHandler;
//
//    /*
//     * 匿名用户访问无权限资源时的异常
//     */
//    @Resource
//    CustomizeAuthenticationEntryPoint authenticationEntryPoint;
//
//    /*
//     * 会话失效(账号被挤下线)处理逻辑
//     */
////    @Resource
////    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;
//
//    /*
//     * 登出成功处理逻辑
//     */
//    @Resource
//    CustomizeLogoutSuccessHandler logoutSuccessHandler;
//
//    /*
//     * 动态权限决策管理器，用于判断用户是否有访问权限
//     */
////    @Resource
////    DynamicAccessDecisionManager accessDecisionManager;
//
//    /*
//     * 动态权限数据源，用于获取动态权限规则
//     */
////    @Resource
////    DynamicSecurityMetadataSource securityMetadataSource;
//
//    /*
//     * 权限拦截器
//     */
////    @Resource
////    private DynamicSecurityInterceptor dynamicSecurityInterceptor;
//
//    /*
//     * 获取用户账号密码及权限信息
//     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new AuthUserServiceImpl();
//    }
//
//    /*
//     * 设置默认的加密方式（强hash方式加密）
//     */
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().updateStatus();
//        http.authorizeRequests().anyRequest().authenticated().and()
//                .formLogin().permitAll()
////                .failureHandler(authenticationFailureHandler)
////                .successHandler(authenticationSuccessHandler)
////            .and().exceptionHandling()
////                .accessDeniedHandler(accessDeniedHandler)
////                .authenticationEntryPoint(authenticationEntryPoint)
//                .and().httpBasic();
////        http.logout().permitAll().logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID");
//
//        /*
//         * 动态权限管理，注入决策管理器和安全元数据源
//         */
////                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
////                    @Override
////                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
////                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
//////                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
////                        return o;
////                    }
////                })
//
////        http.expiredSessionStrategy(sessionInformationExpiredStrategy);
////        http.sessionManagement().maximumSessions(1);
//
//        /*
//         * 动态权限拦截过滤器
//         */
////        http.addFilterBefore(dynamicSecurityInterceptor, FilterSecurityInterceptor.class);
//    }
//
//
////    @Bean
////    public DynamicSecurityService dynamicSecurityService() {
////        return new DynamicSecurityService() {
////            @Override
////            public Map<String, ConfigAttribute> loadDataSource() {
////                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
////                List<UmsResource> resourceList = resourceService.listAll();
////                for (UmsResource resource : resourceList) {
////                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
////                }
////                return map;
////            }
////        };
////    }
//
//}
