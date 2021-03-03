package com.myorg.exercise.quiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myorg.exercise.quiz.config.ApplicationProperties;
import com.myorg.exercise.quiz.executor.DatasourceExecutor;
import com.myorg.exercise.quiz.executor.QuizDatasourceExecutor;
import com.myorg.exercise.quiz.response.vo.Quiz;
import com.myorg.exercise.quiz.response.vo.QuizResponse;

/**
 * The service class which help invoking the configured data-sources and providing Quiz data
 * 
 * @author mhsiraj
 *
 */
@Service
public class QuizService {

	
	@Autowired
	private ApplicationProperties app;
	
	@Autowired
	private RestTemplate restTemplate;
	

	/**
	 * The service method which initiates execution of all data-sources 
	 * collects the data and transforms to the response format
	 * 
	 * @return QuizResponse - the quiz data response
	 */
	public QuizResponse getAllQuizData() {

		/* ********************************************************************
		 * Invoke executing of data-sources
		 * Supplies collection of configured data-sources and the restTemplate
		 * ********************************************************************/
		DatasourceExecutor dsExecutor = new QuizDatasourceExecutor()::getQuizData;
		List<Quiz> quizData = dsExecutor.execute(app.getDatasources(), restTemplate);
		
		// Set the output to the QuizResponse object
		QuizResponse quizResponse = new QuizResponse();
		quizResponse.setQuiz(quizData);
		
		return quizResponse;
	}


}
