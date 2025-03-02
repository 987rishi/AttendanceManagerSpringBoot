package com.rishi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.model.AttendanceHelper;
import com.rishi.model.Course;
import com.rishi.model.Student;
import com.rishi.model.StudentWrapper;
import com.rishi.model.Teacher;
import com.rishi.model.TeacherWrapper;
import com.rishi.service.TeacherService;

import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = "http://localhost:5173")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	
	@GetMapping("teachers")
	public ResponseEntity<List<TeacherWrapper>> getAllTeachers()
	{
		return teacherService.getAll();
	}
	
	@GetMapping("teacher/{id}")
	public ResponseEntity<TeacherWrapper> getTeacher(@PathVariable @Positive Integer id){
		return teacherService.getById(id);
	}
	
	@GetMapping("teacher/{teacherId}/teaching/{courseId}")
	public ResponseEntity<List<StudentWrapper>> getAllStudentsInCourse(@PathVariable @Positive Integer courseId, @PathVariable @Positive Integer teacherId)
	{
		return teacherService.getAllStudentsInSpecificCourse(courseId,teacherId);
	}
	@PostMapping("teacher")
	public ResponseEntity<Teacher> getTeacherByEmail(@RequestBody Map<String,String> reqBody){
		System.out.println(reqBody.get("email"));
		return teacherService.getTeacherByEmail(reqBody.get("email"));
		
	}
	
	
	@PostMapping("teacher/{id}/course")
	public ResponseEntity<String> registerCourses(@PathVariable @Positive Integer id,@RequestBody List<Course> courses)
	{
		return teacherService.registerCoursesByTeacherId(id,courses);
	}
	
	@PostMapping("mark/teacher/{teacherId}/course/{courseId}/student/{studentId}")
	public ResponseEntity<String> markAttendance(@PathVariable Integer teacherId,@PathVariable Integer courseId,@PathVariable Integer studentId,@RequestParam boolean present){
		
		return teacherService.markAttdanceOfStudent(teacherId,courseId,studentId,present);
		
	}
	@PostMapping("attendance/absentees")
	public ResponseEntity<String> markAttendanceByIdsOfAbsentees(@RequestBody AttendanceHelper helper)
	{
		return teacherService.markAttendanceOfAbsentees(helper);
				
	}
}
