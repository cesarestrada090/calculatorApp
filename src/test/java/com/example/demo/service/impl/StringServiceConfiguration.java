package com.example.demo.service.impl;

import com.example.demo.service.RecordService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
class StringServiceConfiguration {
    @Bean
    @Primary
    public RecordService recordService() {
        return Mockito.mock(RecordService.class);
    }
}