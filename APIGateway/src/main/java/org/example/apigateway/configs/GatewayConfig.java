package org.example.apigateway.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {
    private final AuthenticationFilter filter;

    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("ListingService", r -> r.path("/api/listing/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://ListingService"))
                .route("SecurityService", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://SecurityService"))
                .build();
    }
}