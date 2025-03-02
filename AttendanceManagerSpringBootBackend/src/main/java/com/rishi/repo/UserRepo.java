package com.rishi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rishi.model.AppUser;


@Repository
public interface UserRepo extends JpaRepository<AppUser, Integer> {

	@Query("select u from AppUser u join fetch u.roles where u.username=:username")
	Optional<AppUser> findByUsername(String username);

}
