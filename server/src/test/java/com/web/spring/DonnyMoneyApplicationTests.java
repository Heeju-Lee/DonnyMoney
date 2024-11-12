package com.web.spring;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.web.spring.dto.child.wish.WishRequestDto;
import com.web.spring.service.ChildService;

@SpringBootTest
class DonnyMoneyApplicationTests {
    @Autowired 
    ChildService childService;
	
	@Test
	void createWish() throws IOException {

		WishRequestDto wish = new WishRequestDto(1L, null, "IPhone", 1650000, 0, null);
		
        // 파일을 MockMultipartFile로 생성 (파일 이름, 콘텐츠 타입, 파일 내용)
        MockMultipartFile file = new MockMultipartFile(
                "file",                              // 파일 필드 이름
                "iphone.jpg",                        // 파일 이름
                "image/jpeg",                        // 콘텐츠 타입
                new ByteArrayInputStream("image data".getBytes()) // 파일 내용 (byte 배열로 변환)
        );
        
        // createWish 메서드 호출
        System.out.println(childService.createWish(1L,wish, file));
	}
	
	
	
	
	
	
	@Test
	void contextLoads() {
	}

}
