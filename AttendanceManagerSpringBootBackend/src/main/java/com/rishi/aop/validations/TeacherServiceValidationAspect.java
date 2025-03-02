package com.rishi.aop.validations;

import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rishi.model.Course;
import com.rishi.model.Teacher;
import com.rishi.repo.CourseRepo;
import com.rishi.repo.TeacherRepo;

@Aspect
@Component
public class TeacherServiceValidationAspect {
	private final static Logger logger=LoggerFactory.getLogger(TeacherServiceValidationAspect.class);
	

	@Autowired
	private TeacherRepo teacherRepo;
	@Autowired
	private CourseRepo courseRepo;
	
	@Before("execution(* com.rishi.service.TeacherService.getAllStudentsInSpecificCourse(..)) && args(courseId,teacherId)")
	public void validateTeacherTeachingCourse(JoinPoint jp,Integer courseId,Integer teacherId) throws IllegalAccessException {
		logger.info("Validating method:{} in TeacherService.class",jp.getSignature());
		
		if(courseId<=0||teacherId<=0)
		{
			logger.error("Invalid Ids courseId:{} ,teacherId:{}, in method:{}",courseId,teacherId,jp.getSignature());
			throw new IllegalArgumentException("EITHER COURSEID OR TEACHER ID IS NOT VALID(NEGATIVE OR 0) IN METHOD "+jp.getSignature());
		}
		
		Course course =courseRepo.findById(courseId).orElseThrow(()->new NoSuchElementException("COURSE NOT FOUND IN DB WITH COURSEID:"+courseId));
		
		Teacher teacher =teacherRepo.findById(teacherId).orElseThrow(()->new NoSuchElementException("TEACHER WITH TEACHER ID :"+teacherId+" DOES NOT EXIST"));
		
		//VALIDATING TEACHER TEACHING THE COURSE
		
		if(!teacher.getCoursesTeachingIds().contains(course.getCourseId())) {
			logger.error("TEACHER WITH ID:{} IS NOT TEACHING THE COURSE WITH ID:{}",teacherId,courseId);
			throw new IllegalAccessException("TEACHER WITH TEACHER ID:"+teacherId+" IS NOT TEACHING THE COURSE WITH COURSE ID :"+courseId);
			
		}
		
		
		logger.info("Validation of method:{} has finished successfully.",jp.getSignature());
		
	}
	
}
