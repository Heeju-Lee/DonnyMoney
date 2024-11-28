package com.web.spring.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.spring.dto.notification.NotificationResponseDto;
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
	
	private String message; // 메시지 내용
	
	private String category; // 알림 유형
	
	private String senderType; // "parent", "child"

    @ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "parent_num")
	private Parent parent;
	
    @ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "child_num")
	private Child child;
    
    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false; // 기본값: 읽지 않음


	public Notification(Parent parent, Child child, String message, String category, String senderType) {
		this.message = message;
		this.category = category;
		this.senderType = senderType;
		this.parent = parent;
		this.child = child;
	       this.isRead = false; 
	}
	
    public NotificationResponseDto toNotification( Notification notification) {
    	
    	return NotificationResponseDto.builder()
    			.notiNum(notification.getNotiNum()) // notiNum을 추가
    			.parentNum(notification.getParent().getParentNum())
    			.childNum(notification.getChild().getChildNum())
    			.message(notification.getMessage())
    			.category(notification.getCategory())
    			.senderType(notification.getSenderType())
    			.isRead(notification.getIsRead()) // 읽음 여부 추가
    			.build();
    			
    }

	@Override
	public String toString() {
		return "Notification [notiNum=" + notiNum + ", message=" + message + ", category=" + category + ", senderType="
				+ senderType + ", parent=" + parent + ", child=" + child + ", isRead=" + isRead + "]";
	}


	
}
