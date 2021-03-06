package com.myorg.exercise.quiz.executor;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.myorg.exercise.quiz.config.ApplicationProperties;
import com.myorg.exercise.quiz.ds.vo.QuizDatasourceVO;
import com.myorg.exercise.quiz.ds.vo.Result;
import com.myorg.exercise.quiz.response.vo.Quiz;
import com.myorg.exercise.quiz.response.vo.QuizData;

import lombok.extern.slf4j.Slf4j;


/**
 * Execute all configured data-sources in parallel threads concurrently
 * To enable dynamic execution, all similar data-sources can be configured 
 * in application.yml file as app.datasources[0], app.datasources[1], etc
 * 
 * @author mhsiraj
 *
 */
@Component
@Slf4j
public class QuizDatasourceExecutor {
	
	@Autowired
	private ApplicationProperties app;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * Method to execute the data-sources in parallel threads
	 * transforms the data
	 * collects from all executions and returns as collection of quiz data
	 * 
	 * @param datasources - the data-sources to be invoked for execution
	 * @param restTemplate - to hit the external service end-points
	 * @return List<Quiz> - collection of quiz data
	 */
	public List<Quiz> getQuizData() {
		
		/* *******************************************************************************************************************
		 * Execute the data-sources asynchronously in parallel threads.
		 * configure all data-sources in application.yml or on the corresponding profile.
		 * This block will dynamically and concurrently executes all the configured data-sources
		 * *******************************************************************************************************************/
		log.info("Executing rest datasources concurrently...");
		List<CompletableFuture<Quiz>> dsFutures = app.getDatasources().getRestDatasources().stream().map(ds -> (CompletableFuture.supplyAsync(() -> {
			
			// Get the data from data-source
			QuizDatasourceVO datasourceVO = null;
			datasourceVO = invokeRestService(ds);
					
			Quiz quiz = null;
			if(datasourceVO != null) {
				// Get the quizData in response format
				quiz = getQuiz(datasourceVO);
			}
					
			return quiz;
		}))).collect(Collectors.toList());
		
		
		// Collect the execution output from the Completable futures those were executed in parallel
		// Filter out the data-source responses that are null
		List<Quiz> allQuizData = null;
		allQuizData = dsFutures.stream()
				.map(CompletableFuture::join)
				.filter(qz -> qz != null)
				.collect(Collectors.toList());
		log.info("Collected the execution output...");
		return allQuizData;
	}



	/**
	 * Method to execute one specific rest end-point
	 * 
	 * @param ds - the url to invoke
	 * @return datasourceVO - the response QuizDatasourceVO
	 */
	public QuizDatasourceVO invokeRestService(String ds) {
		QuizDatasourceVO datasourceVO;
		datasourceVO = restTemplate.getForEntity(ds, QuizDatasourceVO.class).getBody();
		log.info("Invoked rest service for the url:- {} ",ds);
		
		return datasourceVO;
	}

	

	/**
	 * A function to convert the data-source data to the response contract format 
	 */
	Function<Result, QuizData> mapQuizData = new Function<Result, QuizData>() {
				
		public QuizData apply(Result result) {
			QuizData quizData = new QuizData();
			quizData.setType(result.getType());
			quizData.setDifficulty(result.getDifficulty());
			quizData.setQuestion(result.getQuestion());
			quizData.setCorrect_answer(result.getCorrect_answer());
			quizData.setAll_answers(result.getIncorrect_answers());
			quizData.getAll_answers().add(result.getCorrect_answer());
			
			// Apply a shuffle on the values to get them rearranged
			Collections.shuffle(quizData.getAll_answers());
			
			return quizData;
		}
	};
	
	
	
	/**
	 * Method which transforms the quiz data into the response contract format
	 * 
	 * @param datasourceVO
	 * @return quiz - the transformed quiz
	 */
	public Quiz getQuiz(QuizDatasourceVO datasourceVO) {
		Quiz quiz = new Quiz();
		quiz.setResults(datasourceVO.getResults().stream()
				.map(mapQuizData)
				.collect(Collectors.<QuizData> toList()));
		quiz.setCategory(getQuizCategory(datasourceVO));
		return quiz;
	}

	
	
	/**
	 * Method to get the category of quiz data
	 * 
	 * @param datasourceVO
	 * @return category of quizData
	 */
	private String getQuizCategory(QuizDatasourceVO datasourceVO) {
		String category = null;
		
		if(!CollectionUtils.isEmpty(datasourceVO.getResults()))
			category = datasourceVO.getResults().get(0).getCategory(); 
			
		return category;
	}

	
}
