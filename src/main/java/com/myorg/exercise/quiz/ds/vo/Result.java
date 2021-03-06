package com.myorg.exercise.quiz.ds.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO class to hold the data-sources value object
 * 
 * @author mhsiraj
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

	private String category;
	private String type;
	private String difficulty;
	private String question;
	private String correct_answer;
	private List<String> incorrect_answers;
	
}
