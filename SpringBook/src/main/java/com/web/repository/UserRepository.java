package com.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.model.User;

public interface UserRepository extends JpaRepository<User, Integer> { 
	Optional<User> findByUserId(String userId);
	
	@Query(value="select userId from user where id=?1", nativeQuery=true)
	String invited(int id);
}
