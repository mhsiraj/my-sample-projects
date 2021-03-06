package com.myorg.exercise.quiz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class to initialize beans
 * 
 * @author mhsiraj
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class ApplicationConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
