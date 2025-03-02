package com.rishi.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentWrapper {
	private Integer rollNumber;
	private String name;
	private String email;
	private Integer semester;
	private List<Integer> listOfCoursesEnrolled;
	

}
