package com.rishi.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherWrapper {
	private Integer teacherId;
	private String teacherName;
	private String email;
	private List<Integer> courseIds; 

}
