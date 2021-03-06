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

	private AppDataSources datasources;
	
	
	public AppDataSources getDatasources() {
		return datasources;
	}


	public void setDatasources(AppDataSources datasources) {
		this.datasources = datasources;
	}


	public static class AppDataSources {
		List<String> restDatasources;

		public List<String> getRestDatasources() {
			return restDatasources;
		}

		public void setRestDatasources(List<String> restDatasources) {
			this.restDatasources = restDatasources;
		}
	}
	
	
}
