package com.web.spring.dto.child.wish;

import com.web.spring.entity.IsFinish;
import com.web.spring.entity.Wish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishResponseDto {
	
    private Long wishNum;

    private String img;
    private String name;
    private int price;

    private int savingAmt;
    
    private IsFinish isFinish;
//    private String isFinish;
	
	public WishResponseDto(Wish wish) {
		this.wishNum = wish.getWishNum();
		this.img = wish.getImg();
		this.name = wish.getName();
		this.price = wish.getPrice();
		this.savingAmt = wish.getSavingAmt();
		this.isFinish = wish.getIsFinish();
	}
}