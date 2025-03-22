package com.hook4startup.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityFilterLogger {

    @Bean
    public ApplicationRunner logSecurityFilterChain(FilterChainProxy filterChainProxy) {
        return args -> {
            List<SecurityFilterChain> filterChains = filterChainProxy.getFilterChains();
            System.out.println("🔍 Security Filter Chain:");
            filterChains.forEach(chain -> {
                chain.getFilters().forEach(filter ->
                        System.out.println("➡ " + filter.getClass().getSimpleName())
                );
            });
        };
    }
}
