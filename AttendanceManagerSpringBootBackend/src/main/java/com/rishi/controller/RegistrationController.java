package com.rishi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.model.Course;
import com.rishi.model.Student;
import com.rishi.model.StudentRegisterWrapper;
import com.rishi.model.StudentWrapper;
import com.rishi.model.Teacher;
import com.rishi.model.TeacherRegisterWrapper;
import com.rishi.model.TeacherWrapper;
import com.rishi.service.CourseService;
import com.rishi.service.StudentService;
import com.rishi.service.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins="http://localhost:5173")
public class RegistrationController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private CourseService courseService;
	
	@PostMapping("student")
	public ResponseEntity<StudentWrapper> createStudent(@Valid @RequestBody StudentRegisterWrapper studentRegister){
		
		return studentService.createStudent(studentRegister);
	}
	
	@PostMapping("teacher")
	public ResponseEntity<TeacherWrapper> createTeacher(@Valid @RequestBody TeacherRegisterWrapper teacherRegister){
		return teacherService.createTeacher(teacherRegister);
	}
	
	
	@PostMapping("course")
	public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course)
	{
		return courseService.createCourse(course);
	}
	
}
