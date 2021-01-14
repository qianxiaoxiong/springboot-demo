package com.myringle.config;

import com.myringle.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public User getUser(){
       return User.builder().id("1").username("qlb").sex("男").build();

    }
}
