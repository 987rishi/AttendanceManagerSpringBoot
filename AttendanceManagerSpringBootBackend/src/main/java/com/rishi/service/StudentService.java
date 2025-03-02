package com.rishi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rishi.model.AppUser;
import com.rishi.model.Course;
import com.rishi.model.Role;
import com.rishi.model.Student;
import com.rishi.model.StudentRegisterWrapper;
import com.rishi.model.StudentWrapper;
import com.rishi.model.Teacher;
import com.rishi.model.TeacherWrapper;
import com.rishi.repo.CourseRepo;
import com.rishi.repo.StudentRepo;
import com.rishi.repo.UserRepo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Service
public class StudentService {
	@Autowired
	private StudentRepo repo;
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private UserRepo userRepo;

	public ResponseEntity<List<StudentWrapper>> getStudents() {
		List<Student> students=repo.findAll();
		List<StudentWrapper> studentsWrapper=new ArrayList<>();
		for(Student student:students)
			studentsWrapper.add(studentWrapperHelper(student));
			
		
		return ResponseEntity.ok(studentsWrapper);
	}

	public ResponseEntity<StudentWrapper> createStudent(@Valid StudentRegisterWrapper studentRegister) {
		AppUser user=new AppUser();
		Role role=new Role();
		role.setId(2);
		role.setRole("ROLE_STUDENT");
		
		user.setUsername(studentRegister.getUsername());
		user.setEmail(studentRegister.getEmail());
		//System.out.println("STUDENT PASSWD-"+studentRegister.getPassword());
		user.setPassword(studentRegister.getPassword());
		user.setRoles(new HashSet<>(Collections.singleton(role)));
		
		try {
			
			userRepo.save(user);
			
			Student student=new Student();
			student.setName(studentRegister.getName());
			student.setEmail(studentRegister.getEmail());
			student.setSemester(studentRegister.getSemester());
			
			
			return new ResponseEntity<>(studentWrapperHelper(repo.save(student)),HttpStatus.CREATED);
			
		}
		catch(DataException ex) {
			System.err.println("ERROR OCCURED WHILE CREATING STUDENT IN DB" );
			System.err.println(ex);
			ex.getStackTrace();
			throw new DataAccessResourceFailureException("ERROR WHILE SAVING STUDENT IN DB", ex);
		}
		
	}

	public ResponseEntity<StudentWrapper> getStudentById(@Positive Integer id) {
		Student student=repo.findById(id).orElseThrow();
				
		return ResponseEntity.ofNullable(studentWrapperHelper(student));
	}

	
	public ResponseEntity<String> registerCoursesByStudentId(@Positive Integer id, List<Course> courses) {

		Student student =repo.findById(id).orElseThrow();
		List<Integer> availableCourseIds=courseRepo.findAll()
				.stream()
				.map(course->course.getCourseId())
				.collect(Collectors.toList());
	
		for(int i=0;i<courses.size();i++)
			if(!availableCourseIds.contains(courses.get(i).getCourseId()))
				return new ResponseEntity<String>("GIVEN COURSE IS NOT REGISTERED",HttpStatus.BAD_REQUEST);
		
		List<Integer> courseIds=courses
				.stream()
				.map(course->course.getCourseId())
				.collect(Collectors.toList());
		
		student.setListOfCourses(courseIds);
		repo.save(student);
		return new ResponseEntity<String>("Success",HttpStatus.OK);
		
	}
	private TeacherWrapper teacherWrapperHelper(Teacher teacher) {
		TeacherWrapper wrapper=new TeacherWrapper();
		
		wrapper.setTeacherId(teacher.getTeacherId());
		wrapper.setTeacherName(teacher.getName());
		wrapper.setEmail(teacher.getEmail());
		wrapper.setCourseIds(teacher.getCoursesTeachingIds());
		
		return wrapper;
		
	}
	private StudentWrapper studentWrapperHelper(Student student) {
		if(student==null) return null;
		StudentWrapper wrapper=new StudentWrapper();
		
		wrapper.setRollNumber(student.getRollNumber());
		wrapper.setName(student.getName());
		wrapper.setEmail(student.getEmail());
		wrapper.setSemester(student.getSemester());
		wrapper.setListOfCoursesEnrolled(student.getListOfCourses());
		
		return wrapper;
		
	}

	public ResponseEntity<Student> getStudentByEmail(String email) {
		return ResponseEntity.ofNullable(repo.findByEmail(email).orElseThrow());
	}

	public ResponseEntity<?> updateCourses(List<Integer> courseIds,  @Positive Integer studentId) {
		List<Course> updatedCourses=courseRepo.findAllById(courseIds);
		if(updatedCourses==null)
			throw new IllegalArgumentException("COURSE IDS WRONG");
		Student student=repo.findById(studentId).orElseThrow();
		
		student.setListOfCourses(courseIds);
		try {
		return ResponseEntity.ofNullable(repo.save(student));
		}
		catch(DataException ex) {
			System.err.println("ERROR WHILE UPDATING COURSES IN DB");
			System.err.println(ex);
			ex.printStackTrace();
			throw new DataAccessResourceFailureException("ERROR WHILE UPDATING COURSES IN DB", ex);
		}
	}
	
}
