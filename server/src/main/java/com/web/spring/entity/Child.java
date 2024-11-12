package com.web.spring.entity;

import java.util.List;

import com.web.spring.dto.child.ChildRequestDto;
import com.web.spring.global.audit.Auditable;

import java.time.LocalDate;
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
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Child  extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "child_num")
	private Long childNum;
	
	private String id;
	private String pwd;
	private String name;
	private String birthdate;
	private String phone;
	private String email;
    private String role;

	
	@Column(nullable = true)
	private int point;
	
	//퀴즈 카테고리
	@Column(nullable = true)
	private int qExchangeRate; 	// 환율
	
	@Column(nullable = true)
	private int qInvestment;    //투자
	
	@Column(nullable = true)
	private int qWord;			// 경제 용어
	
	@Column(nullable = true)
	private int qGoverment;		// 정부
	
	@Column(nullable = true)
	private int qHistory;		// 경제의 역사

	//부모-아이 연관관계 매핑
	@ManyToOne(fetch =FetchType.EAGER)
	@JoinColumn(name ="parent_num")
	Parent parent;

	@OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "child_num")
	private List<Plan> plans = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "child_num")
	private List<Payment> payments = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "child_num")
	private List<Wish> wishes = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "child_num")
	private List<StockOrder> stockOrders = new ArrayList<>();

	@Override
	public String toString() {
		return "Child{" +
				"childNum=" + childNum +
				", memberFields=" + super.toString() +
				", point=" + point +
				", qExchangeRate=" + qExchangeRate +
				", qInvestment=" + qInvestment +
				", qWord=" + qWord +
				", qGoverment=" + qGoverment +
				", qHistory=" + qHistory +
				", parent=" + parent +
				'}';
	}

	
	
	

}
