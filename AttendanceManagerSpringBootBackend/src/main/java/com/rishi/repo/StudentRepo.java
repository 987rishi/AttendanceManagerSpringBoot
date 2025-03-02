package com.rishi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.rishi.model.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>{

	Optional<Student> findByEmail(String username);

}
