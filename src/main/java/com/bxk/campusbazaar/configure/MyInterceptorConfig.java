package com.bxk.campusbazaar.configure;

import com.bxk.campusbazaar.Interceptor.jwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyInterceptorConfig extends WebMvcConfigurationSupport {

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/v1/login")
                .excludePathPatterns("/api/v1/register")
                .excludePathPatterns("/api/product/getAllProducts")
                .excludePathPatterns("/img/**");
    }

    /**
     * 配置静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + UPLOAD_FOLDER);
    }
}
