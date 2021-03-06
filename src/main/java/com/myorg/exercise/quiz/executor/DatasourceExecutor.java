package com.myorg.exercise.quiz.executor;

import java.util.List;

import com.myorg.exercise.quiz.response.vo.Quiz;

/**
 * A Functional interface abstracting the data-sources execution
 * 
 * @author mhsiraj
 *
 */
public interface DatasourceExecutor {

	public List<Quiz> execute();
	
}
