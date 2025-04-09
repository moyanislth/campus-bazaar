package com.bxk.campusbazaar.configure;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.bxk.campusbazaar.handler.JsonTypeHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.bxk.campusbazaar.mapper")
@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.getTypeHandlerRegistry().register(JsonTypeHandler.class);
    }
}
