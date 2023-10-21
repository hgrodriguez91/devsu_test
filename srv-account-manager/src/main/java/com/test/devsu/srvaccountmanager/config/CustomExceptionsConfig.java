package com.test.devsu.srvaccountmanager.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomExceptionsConfig {

    @Bean
    public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }

}
