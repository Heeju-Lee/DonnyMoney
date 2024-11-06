
package com.web.spring.entity;

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
public class StockOrder extends Auditable {
	@Id
	@Column(name="stock_num")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stockNum;
	
	private String name;
	
	private String code;
	
	private int price;
	
	private int quantity;
	
	private int amt;

}