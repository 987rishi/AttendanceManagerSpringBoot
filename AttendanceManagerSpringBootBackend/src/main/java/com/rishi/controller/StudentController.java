package com.rishi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.model.Course;
import com.rishi.model.Student;
import com.rishi.model.StudentWrapper;
import com.rishi.service.StudentService;

import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

	@Autowired
	private StudentService studentService;
	@GetMapping("students")
	public ResponseEntity<List<StudentWrapper>> getAllStudents(){
		return studentService.getStudents();
		
	}
	@PostMapping("student")
	public ResponseEntity<Student> getStudentByEmail(@RequestBody Map<String,String> reqBody){
		System.out.println(reqBody.get("email"));
		return studentService.getStudentByEmail(reqBody.get("email"));
		
	}
	
	@GetMapping("student/{id}")
	public ResponseEntity<StudentWrapper> getStudent(@PathVariable @Positive Integer id)
	{
		return studentService.getStudentById(id);
	}
	@PostMapping("student/{id}/course")
	public ResponseEntity<String> registerCourses(@PathVariable @Positive Integer id,@RequestBody List<Course> courses)
	{
		return studentService.registerCoursesByStudentId(id,courses);
	}
	
	@PutMapping("student/{studentId}/updateCoursesByIds")
	public ResponseEntity<?> updateCourseList(@RequestBody List<Integer> courseIds,@PathVariable Integer studentId)
	{
		return studentService.updateCourses(courseIds,studentId);
	}
	
}
