package com.bxk.campusbazaar.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.bxk.campusbazaar.handler.JsonTypeHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库与实体类类型转换类JsonTypeHandler注册
 */
@MapperScan("com.bxk.campusbazaar.mapper")
@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.getTypeHandlerRegistry().register(JsonTypeHandler.class);
    }
}
