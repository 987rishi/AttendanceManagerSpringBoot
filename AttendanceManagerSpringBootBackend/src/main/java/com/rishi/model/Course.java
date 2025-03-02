package com.rishi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer courseId;
	@NotBlank
	private String courseName;
	@Positive
	private Integer semesterOfferedIn;
	//@ManyToMany(mappedBy = "coursesTeaching")
	//@ElementCollection
	/* @JoinTable( name = "teaches", joinColumns = @JoinColumn(name="course_id"),
	  inverseJoinColumns = @JoinColumn(name="teacher_id"))
	//@JsonIgnore
	private List<Integer> taughtByIds;*/
	
}
