package com.rishi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.model.Attendance;
import com.rishi.service.AttendanceService;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;
	@GetMapping("student/{studentId}")
	public ResponseEntity<Attendance> getAttendancesById(@PathVariable Integer studentId){
		return attendanceService.getAttendanceById(studentId);
	}
//	@GetMapping("student/email")
//	public ResponseEntity<?>  getAttendanceByEmailAndCourse(@RequestBody Map<String,String> reqBody){
//		System.out.println(reqBody.get("email"));
//		System.out.println(reqBody.get("courseId"));
//		return attendanceService.getAttendanceByCourseIdAndEmail(reqBody.get("email"),reqBody.get("courseId"));
//		
//	}
	@PostMapping("student/email")
	public ResponseEntity<?>  getAttendanceByEmailAndCourse(@RequestBody Map<String,String> reqBody){
		System.out.println(reqBody.get("email"));
		return attendanceService.getAttendanceByEmail(reqBody.get("email"));
		
	}
}
