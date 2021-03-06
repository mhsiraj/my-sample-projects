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
public class QuizDatasourceVO {

	private int response_code;
	private List<Result> results;
	
}
