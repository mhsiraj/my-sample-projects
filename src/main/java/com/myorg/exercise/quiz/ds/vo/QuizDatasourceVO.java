package com.myorg.exercise.quiz.ds.vo;

import java.util.List;

/**
 * POJO class to hold the data-sources value object
 * 
 * @author mhsiraj
 *
 */
public class QuizDatasourceVO {

	private int response_code;
	private List<Result> results;
	
	public int getResponse_code() {
		return response_code;
	}
	public void setResponse_code(int response_code) {
		this.response_code = response_code;
	}
	public List<Result> getResults() {
		return results;
	}
	public void setResults(List<Result> questions) {
		this.results = questions;
	}
	
	
}
