package com.web.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.spring.dto.child.wish.WishRequestDto;
import com.web.spring.entity.Child;
import com.web.spring.entity.Wish;
import com.web.spring.repository.ChildRepository;
import com.web.spring.repository.WishRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishService {
	
	private final WishRepository wishRepository;
	private final ChildRepository childRepository;
	
	@Transactional
	public void createWish(WishRequestDto wishRequestDto) {
		
		Wish wish = wishRequestDto.toWish(wishRequestDto);
		System.out.println(wish);
					wishRepository.save(wish);
					
		//Child child = child.setPlans(null);
	}
}
