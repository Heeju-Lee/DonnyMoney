package com.web.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.spring.entity.Wish;

import jakarta.transaction.Transactional;


public interface WishRepository extends JpaRepository<Wish, Long>{
	
	// Wish :: 돈모으기
	@Transactional
	@Modifying
	@Query("UPDATE Wish w SET w.savingAmt = ?2 WHERE w.wishNum = ?1")
	int savingWish(Long wishNum, int savingAmt);
	
	// Wish :: isFinish 상태변환
	@Transactional
	@Modifying
	@Query("UPDATE Wish w SET w.isFinish = ?2 WHERE w.wishNum = ?1")
	int isFinish(Long wishNum, Boolean isFinish);
	
	
}
