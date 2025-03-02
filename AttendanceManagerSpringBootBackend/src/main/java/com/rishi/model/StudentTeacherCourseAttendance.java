package com.rishi.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"course_id","student_id","teacher_id"}))
public class StudentTeacherCourseAttendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="course_id",nullable = false)
	private Course course;
	@ManyToOne
	@JoinColumn(name="student_id",nullable = false)
	private Student student;
	@ManyToOne
	@JoinColumn(name="teacher_id",nullable = false)
	private Teacher teacher;
	
	private Integer classesTaken=0;
	@ElementCollection
	@CollectionTable(
			name = "absent_dates",
			joinColumns =@JoinColumn(name="student_teacher_course_id"),
			uniqueConstraints =@UniqueConstraint(columnNames= {"absent_date"}) 
			)
	@Column(name="absent_date")
	private List<LocalDate> classesAbsent=new ArrayList<>();
	
}
