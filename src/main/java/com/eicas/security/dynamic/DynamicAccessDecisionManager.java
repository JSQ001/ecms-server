//package com.eicas.security.dynamic;
//
//import cn.hutool.core.collection.CollUtil;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.Iterator;
//
///**
// * 动态权限决策管理器，用于判断用户是否有访问权限
// *  @author osnudt
// *  @since 2022/4/13
// */
//@Component
//public class DynamicAccessDecisionManager implements AccessDecisionManager {
//
//    @Override
//    public void decide(Authentication authentication,
//                       Object object,
//                       Collection<ConfigAttribute> configAttributes)
//            throws AccessDeniedException, InsufficientAuthenticationException {
//        // 当接口未被配置资源时直接放行
//        if (CollUtil.isEmpty(configAttributes)) {
//            return;
//        }
//
//        for (ConfigAttribute ca : configAttributes) {
//            // 当前请求需要的权限
//            String attribute = ca.getAttribute();
//            // 当前用户所具有的权限
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                if (authority.getAuthority().equals(attribute)) {
//                    return;
//                }
//            }
//        }
//
//        throw new AccessDeniedException("抱歉，您没有访问权限");
//    }
//
//    @Override
//    public boolean supports(ConfigAttribute configAttribute) {
//        return true;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//
//}
