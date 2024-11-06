package com.web.spring.entity;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.web.spring.global.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Plan extends Auditable{
	@Id
	@Column(name="plan_num")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long planNum;
	
	private int shopping;
	
	private int food;
	
	private int transport;
	
	private int cvs;
	
	private int saving;
	
	private int others;

	@Override
	public String toString() {
		return "Plan [planNum=" + planNum + ", shopping=" + shopping + ", food=" + food + ", transport=" + transport
				+ ", cvs=" + cvs + ", saving=" + saving + ", others=" + others + ", getCreatedAt()=" + getCreatedAt()
				+ ", getModifiedAt()=" + getModifiedAt() + "]";
	}
	
}