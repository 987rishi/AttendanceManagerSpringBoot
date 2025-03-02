package com.rishi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.rishi.model.StudentTeacherCourseAttendance;

@Repository
public interface AttendanceRepo extends JpaRepository<StudentTeacherCourseAttendance, Integer>{

	//@NativeQuery(value="select * from ")
	@NativeQuery(value="select * from student_teacher_course_attendance sa,student s where sa.student_id=s.roll_number and sa.course_id=:courseId and sa.student_id=:id")
	List<StudentTeacherCourseAttendance> findAttendanceByStudentId(Integer id, Integer courseId);

	/*
	 * @NativeQuery(value="insert into ") void markAttendance(Integer teacherId,
	 * Integer courseId, Integer studentId);
	 */

}
