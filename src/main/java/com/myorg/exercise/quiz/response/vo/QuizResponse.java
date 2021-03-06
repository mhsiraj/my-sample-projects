package com.myorg.exercise.quiz.response.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResponse {

	List<Quiz> quiz;

	/*
	 * public List<Quiz> getQuiz() { return quiz; }
	 * 
	 * public void setQuiz(List<Quiz> quiz) { this.quiz = quiz; }
	 */
	
	
}
