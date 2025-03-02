package com.rishi.service;


import java.time.LocalDate;
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
import com.rishi.model.AttendanceHelper;
import com.rishi.model.Course;
import com.rishi.model.Role;
import com.rishi.model.Student;
import com.rishi.model.StudentTeacherCourseAttendance;
import com.rishi.model.StudentWrapper;
import com.rishi.model.Teacher;
import com.rishi.model.TeacherRegisterWrapper;
import com.rishi.model.TeacherWrapper;
import com.rishi.repo.AttendanceRepo;
import com.rishi.repo.CourseRepo;
import com.rishi.repo.StudentRepo;
import com.rishi.repo.TeacherRepo;
import com.rishi.repo.UserRepo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@Service
public class TeacherService {

	
	@Autowired
	private TeacherRepo repo;
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private AttendanceRepo attendanceRepo;
	
	@Autowired
	private UserRepo userRepo;

	public ResponseEntity<TeacherWrapper> createTeacher(@Valid TeacherRegisterWrapper teacherRegister) {
		AppUser user=new AppUser();
		Role role=new Role();
		role.setId(3);
		role.setRole("ROLE_TEACHER");
	
		user.setUsername(teacherRegister.getUsername());
		user.setEmail(teacherRegister.getEmail());
		user.setPassword(teacherRegister.getPassword());
		user.setRoles(new HashSet<>(Collections.singleton(role)));
		try {
			userRepo.save(user);
			
			Teacher teacher=new Teacher();
			teacher.setName(teacherRegister.getName());
			teacher.setEmail(teacherRegister.getEmail());
			repo.save(teacher);
			
			return new ResponseEntity<>(teacherWrapperHelper(teacher),HttpStatus.CREATED);
		}
		catch(DataException ex) {
			System.err.println("ERROR WHILE CREATING TEACHER IN DB");
			System.err.println(ex);
			ex.printStackTrace();
			throw new DataAccessResourceFailureException("ERROR WHILE CREATING TEACHER IN DB", ex);
		}
	}
		

	public ResponseEntity<List<TeacherWrapper>> getAll() {
		List<Teacher> teachers=repo.findAll();
		
		List<TeacherWrapper> teacherWrappers=new ArrayList<>();
		
		for(Teacher teacher:teachers)
			teacherWrappers.add(teacherWrapperHelper(teacher));
		
		return ResponseEntity.ok(teacherWrappers);
	}

	public ResponseEntity<TeacherWrapper> getById(@Positive Integer id) {
		Teacher teacher=repo.findById(id).orElse(null);
		return ResponseEntity.ok(teacherWrapperHelper(teacher));
	}

	public ResponseEntity<List<StudentWrapper>> getAllStudentsInSpecificCourse(@Positive Integer courseId,
			@Positive Integer teacherId) {
		//validate that the teacher is teaching the course
		
		List<Student> students=repo.findAllStudentsTeacherTeachingByCourseId(courseId,teacherId);
		List<StudentWrapper> studentsWrapper=new ArrayList<>();
		for(Student student:students)
			studentsWrapper.add(studentWrapperHelper(student));
			
		return ResponseEntity.ok(studentsWrapper);
	}


	public ResponseEntity<String> registerCoursesByTeacherId(@Positive Integer id, List<Course> courses) {
		Teacher teacher=repo.findById(id).orElse(null);
		
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
		
		teacher.setCoursesTeachingIds(courseIds);
		repo.save(teacher);
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
		StudentWrapper wrapper=new StudentWrapper();
		
		wrapper.setRollNumber(student.getRollNumber());
		wrapper.setName(student.getName());
		wrapper.setEmail(student.getEmail());
		wrapper.setSemester(student.getSemester());
		wrapper.setListOfCoursesEnrolled(student.getListOfCourses());
		
		return wrapper;
		
	}

	public ResponseEntity<String> markAttdanceOfStudent(Integer teacherId, Integer courseId, Integer studentId, boolean present) {
		List<Student> studentsTeacherTeaching=repo.findAllStudentsTeacherTeachingByCourseId(courseId, teacherId);
		//System.out.println(studentsTeacherTeaching.toString());
		boolean flag=false;
		for(Student student:studentsTeacherTeaching)
			if(student.getRollNumber().equals(studentId))
			{	
				flag=true;
				break;
			
			}
			if(!flag)
				return ResponseEntity.badRequest().body("STUDENT IS NOT ENROLLED IN THIS COURSE UNDER THIS PROFESSOR");
		
//		attendanceRepo.markAttendance(teacherId,courseId,studentId);
		
		Student student=studentRepo.findById(studentId).orElse(null);
		Teacher teacher=repo.findById(teacherId).orElse(null);
		Course course=courseRepo.findById(courseId).orElse(null);
		StudentTeacherCourseAttendance attendance=new StudentTeacherCourseAttendance();
		if(student!=null&&teacher!=null&&course!=null) {
			
			attendance.setStudent(student);
			attendance.setCourse(course);
			attendance.setTeacher(teacher);
			attendance.setClassesTaken(attendance.getClassesTaken()+1);
			if(!present)
			{
				/*
				 * AbsentDate date=new AbsentDate(); date.setDateOfAbsence(LocalDate.now());
				 */
				attendance.getClassesAbsent().add(LocalDate.now());
			}
			
			
		}
		
		attendanceRepo.save(attendance);
		
		return ResponseEntity.ofNullable("Success");
		
	}
	

	public ResponseEntity<Teacher> getTeacherByEmail(String email) {
		if(email==null)
			throw new IllegalArgumentException("EMAIL PROVIDED IS NULL");
		return ResponseEntity.ofNullable(repo.findByEmail(email).orElse(null));
	}

	public ResponseEntity<String> markAttendanceOfAbsentees(AttendanceHelper helper) {
		//VALIDATE ALL STUDENT IDS GIVEN ARE PART OF THE COURSE TEACHER IS TEACHING
		//VALIDATE TEACHER IS TEACHING THE COURSE
		//VALIDATE THE COURSE EXISTS
		List<Student> studentsEnrolled=repo.findAllStudentsTeacherTeachingByCourseId(helper.getCourseId(), helper.getTeacherId());
		Course course=courseRepo.findById(helper.getCourseId()).orElse(null);
		Teacher teacher=repo.findById(helper.getTeacherId()).orElse(null);
		if(studentsEnrolled==null||course==null||teacher==null)
			throw new IllegalArgumentException("NO STUDENTS ENROLLED");
		for(Student student:studentsEnrolled)
		{
			StudentTeacherCourseAttendance attendance=new StudentTeacherCourseAttendance();
			attendance.setCourse(course);
			attendance.setStudent(student);
			attendance.setTeacher(teacher);
			attendance.setClassesTaken(attendance.getClassesTaken()+1);
			if(helper.getAbsenteesIds().contains(student.getRollNumber()))
				attendance.getClassesAbsent().add(LocalDate.now());
			try {
				attendanceRepo.save(attendance);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.err.println(e.getStackTrace());
				return ResponseEntity.badRequest().body(e.getMessage());
				
			}
			
		}
		return ResponseEntity.ok("SUCCESS");
		
		
	}
	
}
