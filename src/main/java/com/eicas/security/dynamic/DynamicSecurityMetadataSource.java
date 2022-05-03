//package com.eicas.security.dynamic;
//
//import com.eicas.security.entity.AuthPermit;
//import com.eicas.security.service.IAuthPermitService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.List;
//
///**
// * 动态权限数据源，用于获取动态权限规则
// * Created by macro on 2020/2/7.
// */
//@Component
//public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//    @Autowired
//    IAuthPermitService permitService;
//
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
//        // 获取请求地址
//        String requestUrl = ((FilterInvocation) o).getRequestUrl();
//
//        // 查询具体某个接口的权限
//        List<AuthPermit> permissionList = permitService.listPermitsByRequestPath(requestUrl);
//        if (permissionList == null || permissionList.size() == 0) {
//            //请求路径没有配置权限，表明该请求接口可以任意访问
//            return null;
//        }
//        String[] attributes = new String[permissionList.size()];
//        for (int i = 0; i < permissionList.size(); i++) {
//            attributes[i] = permissionList.get(i).getPermitKey();
//        }
//        return SecurityConfig.createList(attributes);
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return false;
//    }
//}
