package com.bukhmastov.teacheritmo.factory;

import com.bukhmastov.teacheritmo.controller.ReviewController;
import com.bukhmastov.teacheritmo.controller.TeacherController;
import com.bukhmastov.teacheritmo.controller.error.AdviceController;
import com.bukhmastov.teacheritmo.controller.error.ErrorController;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ControllerConfiguration {

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
