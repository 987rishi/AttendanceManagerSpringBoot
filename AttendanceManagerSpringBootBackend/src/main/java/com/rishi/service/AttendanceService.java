package com.rishi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rishi.model.Attendance;
import com.rishi.model.AttendancePerSubject;
import com.rishi.model.Student;
import com.rishi.model.StudentTeacherCourseAttendance;
import com.rishi.repo.AttendanceRepo;
import com.rishi.repo.StudentRepo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

@Service
public class AttendanceService {

	@Autowired
	private StudentRepo studentRepo;
	@Autowired
	private AttendanceRepo attendanceRepo;
	
	public ResponseEntity<Attendance> getAttendanceById(@Positive Integer studentId) {
		//WILL THROW A NOSUCHELEMENTEXCEPTION
		Student student=studentRepo.findById(studentId).orElseThrow();
		
		Attendance attendance =new Attendance();
		attendance.setId(student.getRollNumber());
		attendance.setStudentName(student.getName());
		
		for(Integer courseId:student.getListOfCourses())
		{
			
			List<StudentTeacherCourseAttendance> attendForAllTeachers=attendanceRepo.findAttendanceByStudentId(student.getRollNumber(),courseId);
			
			if(attendForAllTeachers.isEmpty())continue;
			
			StudentTeacherCourseAttendance agglomerated= agglomerateAttendance(attendForAllTeachers);
			AttendancePerSubject sub=new AttendancePerSubject();
			sub.setCourseId(courseId);
			
			sub.setCourseName(agglomerated.getCourse().getCourseName());
			
			sub.setAttendanceTillNow(findAttendancePercentage(agglomerated));
			sub.getAbsentDates().addAll(agglomerated.getClassesAbsent());
				
			
			attendance.getAttendanceForEnrolledCourses().add(sub);
		}
		
		return ResponseEntity.ok(attendance);
		
	}



	public ResponseEntity<?> getAttendanceByEmail(@Email String email) {
		if(email==null)
			throw new IllegalArgumentException("email is null");
		//WILL THROW A NOSUCHELEMENTEXCEPTION
		Student student=studentRepo.findByEmail(email).orElseThrow();
		return ResponseEntity.ok(getAttendanceById(student.getRollNumber()));
	} 
	
	
	
	private StudentTeacherCourseAttendance agglomerateAttendance(
			List<StudentTeacherCourseAttendance> attendForAllTeachers) {
		if(attendForAllTeachers==null) return null;
		//System.out.println(attendForAllTeachers.toString());
		StudentTeacherCourseAttendance temp=new StudentTeacherCourseAttendance();
		temp.setStudent(attendForAllTeachers.getFirst().getStudent());
		temp.setCourse(attendForAllTeachers.getFirst().getCourse());
		temp.setTeacher(null);
		
		for(StudentTeacherCourseAttendance st:attendForAllTeachers)
		{
			temp.getClassesAbsent().addAll(st.getClassesAbsent());
			temp.setClassesTaken(temp.getClassesTaken()+st.getClassesTaken());
			
		}
		return temp;
		
		
	}
	private double findAttendancePercentage(StudentTeacherCourseAttendance agglomerated) {
		double percentage=((agglomerated.getClassesTaken()- agglomerated.getClassesAbsent().size())/agglomerated.getClassesTaken())*100;
		return percentage;
	}

}
