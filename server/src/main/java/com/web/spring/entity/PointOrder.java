package com.web.spring.entity;
import java.sql.Date;
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
public class PointOrder extends Auditable{
	
	@Id
	@Column(name = "order_num")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderNum;

	private int amount;
	private String payType;
	
	@Override
	public String toString() {
		return "PointOrder [orderNum=" + orderNum + ", amount=" + amount + ", payType=" + payType + ", getCreatedAt()="
				+ getCreatedAt() + ", getModifiedAt()=" + getModifiedAt() + "]";
	}

	

}
