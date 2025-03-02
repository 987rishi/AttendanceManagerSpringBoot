package com.rishi.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rishi.model.Student;
import com.rishi.model.Teacher;

import jakarta.validation.constraints.Positive;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Integer>{

	@NativeQuery(value = "select * from student s,student_course rs,teacher_courses t where s.roll_number=rs.student_id and rs.course_id=:courseId and t.teacher_id=:teacherId and t.course_id=:courseId")
	//@NativeQuery(value="select * from student")
	List<Student> findAllStudentsTeacherTeachingByCourseId(Integer courseId,Integer teacherId);

	Optional<Teacher> findByEmail(String email);
}
