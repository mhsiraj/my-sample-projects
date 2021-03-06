package com.myorg.exercise.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.exercise.quiz.response.vo.QuizResponse;
import com.myorg.exercise.quiz.service.QuizService;

import lombok.extern.slf4j.Slf4j;

/**
 * The controller class which provides end-point for getting the Quiz data
 * 
 * @author mhsiraj
 *
 */
@RestController
@RequestMapping("/exercise")
@Slf4j
public class QuizController {

	@Autowired
	private QuizService quizService;
	
	@GetMapping(value = "/quiz", produces = MediaType.APPLICATION_JSON_VALUE)
	public QuizResponse getAllQuestions() {
		log.debug("Received the request...");
		QuizResponse quiz = quizService.getAllQuizData();
		log.debug("Received the response...");
		return quiz;
	}
}
