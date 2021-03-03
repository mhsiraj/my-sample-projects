package com.myorg.exercise.quiz.response.vo;

import java.util.List;

public class Quiz {

	private String category;
	private List<QuizData> results;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public List<QuizData> getResults() {
		return results;
	}
	public void setResults(List<QuizData> results) {
		this.results = results;
	}
	
	
	
}
