package com.rishi.aop.validations;

import java.util.List;
import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rishi.repo.CourseRepo;

@Aspect
@Component
public class CourseServiceValidationAspect {
	@Autowired
	private CourseRepo courseRepo;
	
	private final static Logger logger=LoggerFactory.getLogger(CourseServiceValidationAspect.class);
	
	@Before("execution(* com.rishi.service.CourseService.getCoursesByIds(..)) &&args(courseIds)")
	public void validateCoursesExist(JoinPoint jp,List<Integer> courseIds)
	{
		logger.info("Validating method:{} in CourseService.class",jp.getSignature());
		
		 for(Integer id:courseIds)
			 if(!courseRepo.existsById(id))
			 {
				 logger.error("This courseId:{} does not exist in db",id);
				 throw new NoSuchElementException("THE COURSE ID "+id+" does not exist");
			 }
				
		 logger.info("Validation of method:{} finished successfully",jp.getSignature());
	}

}
