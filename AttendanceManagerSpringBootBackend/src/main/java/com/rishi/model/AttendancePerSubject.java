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
public class AttendancePerSubject {
	private Integer courseId;
	private String courseName;
	private Double attendanceTillNow;
	private List<LocalDate> absentDates=new ArrayList<>();
	

}
