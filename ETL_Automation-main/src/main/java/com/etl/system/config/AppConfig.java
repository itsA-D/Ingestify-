package com.etl.system.config;

import com.etl.system.service.analyzer.DataAnalyzerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public DataAnalyzerFactory dataAnalyzerFactory() {
        return new DataAnalyzerFactory();
    }
}