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
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer teacherId;
	@NotBlank
	private String name;
	@Email
	private String email;
	@ElementCollection
	@CollectionTable(
			name = "teacher_courses",
			joinColumns = @JoinColumn(name="teacher_id")
			)
	/*
	 * @JoinTable( name = "teaches", joinColumns = @JoinColumn(name="teacher_id"),
	 * inverseJoinColumns = @JoinColumn(name="course_id"))
	 */
	@Column(name = "course_id")
	private List<Integer> coursesTeachingIds;
	

}
