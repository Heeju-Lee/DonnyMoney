package com.web.spring.repository;

import com.web.spring.entity.Payment;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.spring.entity.Child;
import com.web.spring.entity.Parent;

import java.util.List;
import com.web.spring.entity.Payment;
import com.web.spring.entity.Plan;
import com.web.spring.entity.Wish;
import com.web.spring.entity.Plan;
import com.web.spring.entity.Quiz;

import java.util.ArrayList;
import java.util.List;
import jakarta.transaction.Transactional;


public interface ChildRepository extends JpaRepository<Child, Long>{

	Child findById(String id);
	
	Boolean existsById(String id);

	@Query(value ="SELECT c FROM Child c WHERE c.id = :id")
	Child duplicateCheck(String  id);

	@Query("SELECT p FROM Child c JOIN c.plans p WHERE c.childNum = :childNum AND FUNCTION('YEAR', p.createdAt) = :year AND FUNCTION('MONTH', p.createdAt) = :month")
	Plan findPlan( @Param("childNum") Long childNum,@Param("year") int year, @Param("month")  int month);
	
	// 포인트 잔액조회
	@Query(value ="SELECT c.point FROM Child c WHERE c.childNum = :childNum")
	Optional<Integer> showPoint(@Param("childNum") Long childNum);
	
	// 포인트 업데이트
	@Transactional
	@Modifying
	@Query(value ="UPDATE Child c SET c.point = :point WHERE c.childNum = :childNum")
	int updatePoint(@Param("point")int point, @Param("childNum") Long childNum);

	//MGMT-23(특정 년/월의 소비를 보여주기)
	@Query("SELECT p FROM Child c JOIN c.payments p where c.childNum =:childNum")
	List<Payment> showMonthPayments(@Param("childNum") Long childNum);

	// Wish :: Active 상태의 위시리스트만 조회
	@Query("SELECT w FROM Child c JOIN c.wishes w where c.childNum =:childNum AND w.isFinish = com.web.spring.entity.IsFinish.INCOMPLETE")
    List<Wish> showActiveWishList(@Param("childNum") Long childNum);
	
	// Wish :: Finished 상태의 위시리스트만 조회
	@Query("SELECT w FROM Child c JOIN c.wishes w where c.childNum =:childNum AND w.isFinish = com.web.spring.entity.IsFinish.COMPLETE")
    List<Wish> showFinishedWishList(@Param("childNum") Long childNum);

	//퀴즈 보여주기 (랜덤)
    @Query("SELECT q FROM Quiz q WHERE q.category = :category ORDER BY RAND()")
    List<Quiz> findQuizByCategoryRandom(@Param("category") String category);

	
    
    
}
