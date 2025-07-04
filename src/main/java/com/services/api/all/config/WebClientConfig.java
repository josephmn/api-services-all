package com.services.api.all.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientConfig class configures WebClient beans for external API calls.
 * It provides two WebClient instances for Luz del Sur and Agua Cañete.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a WebClient bean for Luz del Sur API.
     * The base URL is set to "https://www.luzdelsur.pe" and the default header is set to "Content-Type: application/json".
     *
     * @param builder WebClient.Builder instance
     * @return WebClient instance configured for Luz del Sur
     */
    @Bean
    public WebClient luzDelSurWebClient(WebClient.Builder builder) {
        return WebClient.builder()
                .baseUrl("https://www.luzdelsur.pe")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    /**
     * Creates a WebClient bean for Agua Cañete API.
     * The base URL is set to "https://aguacanete.com".
     *
     * @param builder WebClient.Builder instance
     * @return WebClient instance configured for Agua Cañete
     */
    @Bean
    public WebClient aguaCaneteWebClient(WebClient.Builder builder) {
        return WebClient.builder()
                .baseUrl("https://aguacanete.com")
                .build();
    }
}
