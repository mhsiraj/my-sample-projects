package com.myorg.exercise.quiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myorg.exercise.quiz.executor.DatasourceExecutor;
import com.myorg.exercise.quiz.executor.QuizDatasourceExecutor;
import com.myorg.exercise.quiz.response.vo.Quiz;
import com.myorg.exercise.quiz.response.vo.QuizResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * The service class which help invoking the configured data-sources and providing Quiz data
 * 
 * @author mhsiraj
 *
 */
@Service
@Slf4j
public class QuizService {


	@Autowired
	private QuizDatasourceExecutor quizDSExecutor;
	
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
		log.debug("Inside getAllQuizData...");
		DatasourceExecutor dsExecutor = quizDSExecutor::getQuizData;
		List<Quiz> quizData = dsExecutor.execute();
		
		// Set the output to the QuizResponse object
		QuizResponse quizResponse = new QuizResponse();
		quizResponse.setQuiz(quizData);
		
		log.debug("Exiting getAllQuizData...");
		return quizResponse;
	}


}
