package com.myorg.exercise.quiz.test.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.exercise.quiz.response.vo.QuizResponse;
import com.myorg.exercise.quiz.service.QuizService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class QuizControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private QuizService quizService;
	
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
		
		given(quizService.getAllQuizData()).willReturn(quizResponse);
	}

	@Test
	public void whenGetAllQuestions_thenCheckStatusOK() throws Exception {
		
		mvc.perform(get("/exercise/quiz")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void whenGetAllQuestions_thenReturnQuizResponse() throws Exception {
		
		mvc.perform(get("/exercise/quiz")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.quiz[0].category", "Entertainment: Film").exists());

		
	}
}
