package com.bxk.campusbazaar.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.bxk.campusbazaar.tools.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, @NotNull Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");

        // 获取 header 里的 token
        final String token;

        log.info("jwt拦截路径: " + request.getRequestURI());

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);  // 去掉 "Bearer " 前缀

        }else {
            token = null;
        }

        // 放行 OPTIONS 请求（如跨域预检请求）
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // 检查 token 是否存在
        if (token == null) {
            response.getWriter().write("未登录");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 验证 token
        Map<String, Claim> userData = jwtUtil.verifyToken(token);

        if (userData == null) {
            response.getWriter().write("登陆凭证过期");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 提取用户信息并存入 request
        Integer id = userData.get("id").asInt();
        String userName = userData.get("userName").asString();
        String role = userData.get("role").asString();

        request.setAttribute("id", id);
        request.setAttribute("userName", userName);
        request.setAttribute("role", role);

        return true;  // 放行请求
    }
}
