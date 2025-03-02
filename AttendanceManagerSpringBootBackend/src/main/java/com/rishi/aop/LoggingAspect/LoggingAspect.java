package com.rishi.aop.LoggingAspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger logger=LoggerFactory.getLogger("ATTENDANCE SERVICE LOGGER");
	
	@Before("com.rishi.aop.PointCuts.serviceAttendanceMethods()")
	public void loggingSignaturesAndArguments(JoinPoint jp)
	{
		logger.info(jp.getSignature()+" Called");
		
		logger.info("ARGS ->",jp.getArgs());
		
	}
	
	@AfterReturning(
		        pointcut = "com.rishi.aop.PointCuts.serviceAttendanceMethods()",
		        returning = "result" // This parameter holds the return value of the method
		    )
	public void logAfterServiceMethodExecution(Object result) {
		        // Log the return value or perform any other post-processing tasks
		        logger.info("Service method executed successfully. Return value: {}", result);
	}
	
	@AfterThrowing(
			pointcut ="com.rishi.aop.PointCuts.serviceAttendanceMethods()",
			throwing = "exception")
	public void logAfterThrowing(Object exception) {
		logger.error("ERROR THROWN--Exception:{}",exception.toString());
		logger.trace(null, exception);
	}
	
}
	
