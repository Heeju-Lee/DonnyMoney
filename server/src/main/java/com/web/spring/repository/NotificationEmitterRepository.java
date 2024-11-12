package com.web.spring.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

// SseEmitter 인스턴스 관리
// SseEmitter를 관리하는 특정 로직을 가진 저장소 역할
public class NotificationEmitterRepository {
	// 모든 Emitters를 저장하는 ConcurrentHashMap
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	
	// Emitters 저장. 클라이언트에게 보낼 id와 데이터를 저장 
	public void save(Long id, SseEmitter emitter) {
		emitters.put(id, emitter);
	}
	
	// Emitter 제거. 정보를 담기 위해 클라이언트 id의 Emitter를 가져와햐함
	public void deleteById(Long id) {
		emitters.remove(id);
	}
	
	// Emitter 가져오기. Emitter가 완료될때, 타임아웃 되었을때, 오류가 발생했을때 사용된다
	public SseEmitter get(Long id) {
		return emitters.get(id);
	}
	
}
