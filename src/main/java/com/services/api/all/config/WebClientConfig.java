package com.services.api.all.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient luzDelSurWebClient(WebClient.Builder builder) {
        return WebClient.builder()
                .baseUrl("https://www.luzdelsur.pe")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean
    public WebClient aguaCaneteWebClient(WebClient.Builder builder) {
        return WebClient.builder()
                .baseUrl("https://aguacanete.com")
                .build();
    }
}
