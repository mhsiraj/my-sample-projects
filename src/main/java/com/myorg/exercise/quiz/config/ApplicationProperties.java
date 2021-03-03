package com.myorg.exercise.quiz.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ApplicationProperties class to map the configuration properties
 * 
 * @author mhsiraj
 *
 */
@Component
@ConfigurationProperties("app")
public class ApplicationProperties {

	List<String> datasources;

	public List<String> getDatasources() {
		return datasources;
	}

	public void setDatasources(List<String> datasources) {
		this.datasources = datasources;
	}
	
	
}
