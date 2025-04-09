package com.bxk.campusbazaar;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bxk.campusbazaar.mapper")
public class CampusBazaarApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusBazaarApplication.class, args);
    }

}
