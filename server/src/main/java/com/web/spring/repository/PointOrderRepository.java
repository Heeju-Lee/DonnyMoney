package com.web.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.spring.entity.PointOrder;

public interface PointOrderRepository extends JpaRepository<PointOrder, Long>{

}
