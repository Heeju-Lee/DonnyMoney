package com.web.spring.repository;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.type.NumericBooleanConverter;
import org.hibernate.type.TrueFalseConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.spring.entity.IsFinish;
import com.web.spring.entity.Wish;
import com.web.spring.global.BooleanToYNConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.transaction.Transactional;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long>{
	
	// Wish :: 돈모으기
	@Transactional
	@Modifying
	@Query("UPDATE Wish w SET w.savingAmt = ?2 WHERE w.wishNum = ?1")
	int savingWish(Long wishNum, int savingAmt);
	
	// Wish :: isFinish 상태변환
	@Transactional
	@Modifying
	@Query("UPDATE Wish w SET w.isFinish =:isFinish WHERE w.wishNum=:wishNum")
	int isFinish(@Param("wishNum")Long wishNum, @Param("isFinish")IsFinish isFinish);
	
	
}
