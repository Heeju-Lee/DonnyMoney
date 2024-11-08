package com.web.spring.entity;

import java.util.List;

import com.web.spring.global.audit.Auditable;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Child extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "c_num")
	private Long childNum;
	
	private String id;
	private String pwd;
	private String name;
	private String birthdate;
	private String phone;
	private String email;
	
	@Column(nullable = true)
	private int point;
	
	//퀴즈 카테고리
	@Column(nullable = true)
	private int qExchangeRate; 	// 환율
	
	@Column(nullable = true)
	private int qWoard;			// 경제 용어
	
	@Column(nullable = true)
	private int qGoverment;		// 정부
	
	@Column(nullable = true)
	private int qHistory;		// 경제의 역사
	
	@OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "c_num", nullable = false, updatable = false) 
	private List<Plan> plans = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "c_num", nullable = false, updatable = false) 
	private List<Payment> payments = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "c_num", nullable = false, updatable = false) 
	private List<Wish> wishs = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "c_num", nullable = false, updatable = false) 
	private List<StockOrder> stockOrders = new ArrayList<>();
	
	@Override
	public String toString() {
		return "Child [childNum=" + childNum + ", id=" + id + ", pwd=" + pwd + ", name=" + name + ", birthdate=" + birthdate
				+ ", phone=" + phone + ", email=" + email + ", point=" + point + ", qExchangeRate=" + qExchangeRate
				+ ", qWoard=" + qWoard + ", qGoverment=" + qGoverment + ", qHistory=" + qHistory + "]";
	}
	
}
