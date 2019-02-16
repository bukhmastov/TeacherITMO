package com.bukhmastov.teacheritmo.config;

import com.bukhmastov.teacheritmo.controller.ReviewController;
import com.bukhmastov.teacheritmo.controller.TeacherController;
import com.bukhmastov.teacheritmo.controller.error.AdviceController;
import com.bukhmastov.teacheritmo.controller.error.ErrorController;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@Configuration
@EnableWebMvc
public class ControllerConfiguration {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    TeacherController getTeacherController() {
        return new TeacherController();
    }

    @Bean
    ReviewController getReviewController() {
        return new ReviewController();
    }

    @Bean
    ErrorController getErrorController() {
        return new ErrorController();
    }

    @Bean
    AdviceController getAdviceController() {
        return new AdviceController();
    }
}
