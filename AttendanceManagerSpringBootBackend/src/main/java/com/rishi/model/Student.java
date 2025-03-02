package com.rishi.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rollNumber;
	
	@NotBlank
	private String name;
	@Email
	private String email;
	private Integer semester;
	@ElementCollection
	/*
	 * @JoinTable( name = "register_courses", joinColumns
	 * = @JoinColumn(name="student_id"), inverseJoinColumns
	 * = @JoinColumn(name="course_id"))
	 */
	@CollectionTable(
			name="student_course",
			joinColumns=@JoinColumn(name="student_id"))
	@Column(name="course_id")
	private List<Integer> listOfCourses;

}
