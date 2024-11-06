package com.web.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import com.web.spring.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long>{


}
