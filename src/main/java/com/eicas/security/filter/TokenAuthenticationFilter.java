package com.eicas.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
 
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 鉴权
 */
@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
 
    public TokenAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }
 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("=================" + request.getRequestURI());
        //需要鉴权

        if (!(request.getRequestURI().contains("/login"))) {
            UsernamePasswordAuthenticationToken authentication = null;
            try {
                authentication = getAuthentication(request);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.error("鉴权失败");
            }
        }
        chain.doFilter(request, response);
    }
 
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 获取Token字符串，token 置于 header 里
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            token = request.getParameter("token");
        }
        if (token != null && !"".equals(token.trim())) {
            // 从Token中解密获取用户名
//            String userName = JwtTokenUtil.getUserNameFromToken(token);
//
//            if (userName != null) {
//                // 从Token中解密获取用户角色
//                String role = JwtTokenUtil.getUserRoleFromToken(token);
//                // 将ROLE_XXX,ROLE_YYY格式的角色字符串转换为数组
//                String[] roles = role.split(",");
//                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                for (String s : roles) {
//                    authorities.add(new SimpleGrantedAuthority(s));
//                }
//                return new UsernamePasswordAuthenticationToken(userName, token, authorities);
//            }
            return null;
        }
        return null;
    }
}