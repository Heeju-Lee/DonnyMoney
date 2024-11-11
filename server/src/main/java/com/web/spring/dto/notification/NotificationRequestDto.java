package com.web.spring.dto.notification;

import jakarta.validation.constraints.NotNull;

//@Getter
//@Setter
public class NotificationRequestDto {
	private Long notiNum;
	
	@NotNull
	private Long parentNum;

	@NotNull
    private Long childNum;
	
	@NotNull
    private String message;
    
	@NotNull
    private String category;
	
	@NotNull
	private String senderType; // "parent" , "child"
	
	public NotificationRequestDto() {}

	public NotificationRequestDto(Long notiNum, @NotNull Long parentNum, @NotNull Long childNum,
			@NotNull String message, @NotNull String category, @NotNull String senderType) {
		this.notiNum = notiNum;
		this.parentNum = parentNum;
		this.childNum = childNum;
		this.message = message;
		this.category = category;
		this.senderType = senderType;
	}

	public Long getNotiNum() {
		return notiNum;
	}

	public void setNotiNum(Long notiNum) {
		this.notiNum = notiNum;
	}

	public Long getParentNum() {
		return parentNum;
	}

	public void setParentNum(Long parentNum) {
		this.parentNum = parentNum;
	}

	public Long getChildNum() {
		return childNum;
	}

	public void setChildNum(Long childNum) {
		this.childNum = childNum;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSenderType() {
		return senderType;
	}

	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}

	
	
}
