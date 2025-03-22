package com.hook4startup.config;

import com.hook4startup.Filter.CustomTokenFilter;
import com.hook4startup.services.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private  CustomerUserDetailsService userDetailsService;
    @Autowired
    private CustomTokenFilter customTokenFilter; // Ensure this is defined in your project

    public SpringSecurity(CustomerUserDetailsService userDetailsService, CustomTokenFilter customTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.customTokenFilter = customTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS ko enable karna
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()// ✅ Public routes
                        .requestMatchers("/user/**", "/post/**", "/comment/**","/like/**","/cloudinary/**").authenticated() // ✅ Protected routes
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);

        http.addFilterBefore(customTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
 @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ Allowed Origins - Trailing slash hatao!
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:3000",
                "http://localhost:4173",
                "https://hook4startup-client.onrender.com"
        ));

        // ✅ Methods Allowed
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ Allowed Headers
        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept"
        ));

        // ✅ Exposed Headers - Set-Cookie ke sath Authorization ko expose karo
        configuration.setExposedHeaders(List.of(
                "Set-Cookie",
                "Authorization"
        ));

        // ✅ Allow credentials for cookies & secure sessions
        configuration.setAllowCredentials(true);

        // ✅ Register for all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }



}
