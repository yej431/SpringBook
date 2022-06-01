package com.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.model.WishList;

public interface WishRepository extends JpaRepository<WishList, Long> { 

	Page<WishList> findByUserId(int userId, Pageable pageable);
}
