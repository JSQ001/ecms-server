package com.eicas.security.handler;

import com.alibaba.fastjson.JSON;
import com.eicas.common.ResultCode;
import com.eicas.common.ResultData;
import com.eicas.security.entity.AuthUser;
import com.eicas.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

/**
 * @author osnudt
 * @since 2022/4/12
 */

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter out = httpServletResponse.getWriter();

        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String token = JwtUtil.createToken(principal.getUsername(),principal.getAuthorities().toString());

        ResultData result = ResultData.success(token,"用户登录成功");
        out.write(JSON.toJSONString(result));
    }
}
