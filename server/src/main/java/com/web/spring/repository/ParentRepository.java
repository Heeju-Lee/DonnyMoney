package com.web.spring.repository;


import com.web.spring.entity.Child;
import com.web.spring.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParentRepository  extends JpaRepository<Parent,Long> {
	
	
	Parent findById(String id);

	@Query(value ="SELECT p FROM Parent p WHERE p.id = :id")
	Parent duplicateCheck(String id);
	


}
