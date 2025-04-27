package com.bxk.campusbazaar;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@MapperScan("com.bxk.campusbazaar.api.mapper")
public class CampusBazaarApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusBazaarApplication.class, args);
    }

}
