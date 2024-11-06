package com.web.spring.repository;


import com.web.spring.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository  extends JpaRepository<Parent,Long> {

}
