package com.web.spring.dto.notification;

import java.util.List;

import com.web.spring.entity.Child;
import com.web.spring.entity.Notification;
import com.web.spring.entity.Parent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class NotificationResponseDto {
	private Long notiNum;

	private Long parentNum;

    private Long childNum;
	
    private String message;
    
    private String category;
    
    private String senderType;
    

	public NotificationResponseDto(Long notiNum, Long parentNum, Long childNum, String message, String category,
			String senderType) {
		this.notiNum = notiNum;
		this.parentNum = parentNum;
		this.childNum = childNum;
		this.message = message;
		this.category = category;
		this.senderType = senderType;
//        this.allNotification = allNotification;
	}
	
//    // 기존 알림 목록을 설정하는 메소드
//    public void setExistingNotifications(List<Notification> existingNotifications) {
//        this.allNotification = existingNotifications;
//    }
//
//    // 기존 알림 목록을 반환하는 메소드
//    public List<Notification> getExistingNotifications() {
//        return this.allNotification;
//    }
    
    
}
