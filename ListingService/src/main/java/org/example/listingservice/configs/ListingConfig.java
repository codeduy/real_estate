package org.example.listingservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class ListingConfig {

    // use to call http request
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
