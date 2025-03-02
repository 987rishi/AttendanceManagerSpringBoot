package com.rishi.aop.validations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAttendanceAspect {
	
	private static final Logger logger=LoggerFactory.getLogger(ValidationAttendanceAspect.class);
	@Before("execution(* com.rishi.service.AttendanceService.getAttendanceById(..)) && args(studentId)")
	public void validateStudentId(JoinPoint jp,Integer studentId) {
		logger.info("VALIDATING method :{} in AttendanceService.class",jp.getSignature());
		if(studentId==null||studentId<=0) {
			logger.error("STUDENT ID:{} IS INVALID",studentId);
			throw new IllegalArgumentException("INVALID STUDENT ID EITHER NULL OR <=0");
		}
		logger.info("VALIDATION OF method :{} in AttendanceService.class HAS FINISHED SUCCESSFULLY",jp.getSignature());
		
	}

	
	
}
