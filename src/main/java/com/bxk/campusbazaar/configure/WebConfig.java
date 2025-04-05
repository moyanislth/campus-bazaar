package com.bxk.campusbazaar.configure;

import com.bxk.campusbazaar.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                // 需要排除contextPath的值
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/v1//login",
                        "/api/v1/register"
                );
    }
}
