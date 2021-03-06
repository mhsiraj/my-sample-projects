package com.myorg.exercise.quiz.test.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.exercise.quiz.executor.QuizDatasourceExecutor;
import com.myorg.exercise.quiz.response.vo.QuizResponse;
import com.myorg.exercise.quiz.service.QuizService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuizServiceTest {

	@Autowired
	private QuizService quizService;
	
	@MockBean
	private QuizDatasourceExecutor quizDSExecutor;
	
	private QuizResponse quizResponse = null;
	
	@Before
	public void setUp() {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			quizResponse = mapper.readValue(getClass().getResourceAsStream("/sample_response.json"), new TypeReference<QuizResponse>() {});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		given(quizDSExecutor.getQuizData()).willReturn(quizResponse.getQuiz());

	}
	
	
	@Test
	public void whenGetAllQuizData_checkQuizResponseNotNull() {
		
		QuizResponse execRespose = quizService.getAllQuizData();
		assertNotNull(execRespose);
	}
	
	@Test
	public void whenGetAllQuizData_checkQuizResponseIsValid() {
		
		QuizResponse execRespose = quizService.getAllQuizData();
		assertEquals(execRespose.getQuiz().get(0).getCategory(), quizResponse.getQuiz().get(0).getCategory());
	}
	
}
