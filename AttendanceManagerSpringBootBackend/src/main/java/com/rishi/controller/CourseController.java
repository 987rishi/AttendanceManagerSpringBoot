package com.rishi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.model.Course;
import com.rishi.service.CourseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins="http://localhost:5173",allowCredentials = "true")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping("courses")
	public ResponseEntity<?> getAllCourses(HttpServletRequest req)
	{
		 HttpSession session = req.getSession(false);
		if (session == null) {
	        System.out.println("Session is null");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session not found");
	    }
	    System.out.println("Session ID: " + session.getId());
		return courseService.getAll();
	}
	

	@GetMapping("course/{id}")
	public ResponseEntity<Course> getCourse(@PathVariable @Positive Integer id)
	{
		return courseService.getCourseById(id);
	}
	@PostMapping("courseByIds")
	public ResponseEntity<List<Course>> getCoursesByIds(@RequestBody List<Integer> courseIds)
	{
		return courseService.getCoursesByIds(courseIds);
	}
	
}
