package com.myorg.exercise.quiz.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

/**
 * Use QuizApplicationLoggerAdvice to watch any method
 * Configure the method using @Pointcut feature
 * Map the method into the @Around annotation
 * 
 * @author mhsir
 *
 */
@Aspect
@Slf4j
@Component
public class QuizApplicationLoggerAdvice {

	/**
	 * Method checks and logs the execution time of every mapped method
	 * IF any mapped method's execution passed a critical time interval it WARNs
	 *  
	 * @param ProceedingJoinPoint - pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("getAllQuizData() || getDsExecutions() ")
	public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
		
		String className = pjp.getSignature().getDeclaringTypeName();
		String methodName = pjp.getSignature().getName();
		
		StopWatch clock = new StopWatch();		
		clock.start();
		
		Object result = pjp.proceed();
		clock.stop();
		long elapsedTime = clock.getTotalTimeMillis();
		
		if(elapsedTime > 2000)
			log.warn("REQUEST TAKING MORE THAN 2 SECONDS TO PROCESS method {}.{}() ... execution time {} ms...", className, methodName, elapsedTime);
		else
			log.info("Method {}.{}() ... execution time {} ms...", className, methodName, elapsedTime);
		
		return result;
	}

	
	@Pointcut("execution(* com.myorg.exercise.quiz.service.QuizService.getAllQuizData(..))")
	public void getAllQuizData() {}
	
	
	@Pointcut("execution(* com.myorg.exercise.quiz.executor.QuizDatasourceExecutor.*(..))")
	public void getDsExecutions() {}
	
	
}
