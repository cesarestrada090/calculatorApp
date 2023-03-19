package com.example.demo;

import com.example.demo.service.RecordService;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableJpaRepositories
class CalculatorConfig {
}