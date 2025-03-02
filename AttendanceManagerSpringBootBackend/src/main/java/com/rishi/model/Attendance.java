package com.rishi.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
	private Integer id;
	private String studentName;
	private List<AttendancePerSubject> attendanceForEnrolledCourses=new ArrayList<>();
	

}
