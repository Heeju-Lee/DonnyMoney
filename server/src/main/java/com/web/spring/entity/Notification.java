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
public class Notification extends Auditable{

	@Id
	@Column(name="noti_num")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notiNum;
	
	private String message;
	
	private String category;

    @ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "parent_num")
	private Parent parent;
	
    @ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "child_num")
	private Child child;

	@Override
	public String toString() {
		return "Notification [Nnum=" + notiNum + ", message=" + message + ", category=" + category + ", parent=" + parent
				+ ", child=" + child + ", getCreatedAt()=" + getCreatedAt() + ", getModifiedAt()=" + getModifiedAt()
				+ "]";
	}
	
}
