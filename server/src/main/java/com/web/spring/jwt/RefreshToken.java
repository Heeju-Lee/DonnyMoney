package com.web.spring.jwt;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@RedisHash(value = "memberId", timeToLive = 14440)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken {
	// redis에 refresh토큰을 저장하기 위한 클래스 key로 memberId, value를 refreshToken
	// 유효시간 timeToLive = 14440(4시간)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;

    @Column(nullable = false, unique = true)
    private String username;
    
	@Column(nullable = false, unique = true)
	private String token;


	 private String expiration;
	 
	 
	 
	 
}