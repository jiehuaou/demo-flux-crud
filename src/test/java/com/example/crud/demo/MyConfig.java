package com.example.crud.demo;

import com.example.crud.demo.logic.MyValidator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MyConfig {
    @Bean
    public MyValidator getValidator(){
        return new MyValidator();
    }
}
