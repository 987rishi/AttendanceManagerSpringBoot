package com.rishi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rishi.model.Course;
import com.rishi.repo.CourseRepo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;



@Service

public class CourseService {

	@Autowired
	private CourseRepo repo;
	
	public ResponseEntity<List<Course>> getAll() {
		return ResponseEntity.ok(repo.findAll());
	}

	public ResponseEntity<Course> createCourse(@Valid Course course) {
		  try {
		        return new ResponseEntity<>(repo.save(course), HttpStatus.CREATED);
		    } catch (DataAccessException ex) {
		        throw new DataAccessResourceFailureException("FAILED TO CREATE COURSE IN DB",ex);
		    }
	}

	public ResponseEntity<Course> getCourseById(@Positive Integer id) {
		return ResponseEntity.ok(repo.findById(id).orElseThrow());
	}

	public ResponseEntity<List<Course>> getCoursesByIds(List<Integer> courseIds) {
		//VALIDATE COURSE IDS ARE POSITIVE AND EXIST
		return ResponseEntity.ofNullable(repo.findAllById(courseIds));
	}

	
}
