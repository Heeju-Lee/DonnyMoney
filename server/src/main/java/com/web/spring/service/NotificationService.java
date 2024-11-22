package com.web.spring.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.spring.dto.notification.NotificationRequestDto;
import com.web.spring.dto.notification.NotificationResponseDto;
import com.web.spring.entity.Child;
import com.web.spring.entity.Notification;
import com.web.spring.entity.Parent;
import com.web.spring.repository.ChildRepository;
import com.web.spring.repository.NotificationRepository;
import com.web.spring.repository.ParentRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	// Notification 객체 생성, 저장, SSE로 전송하는 기능
	// 알림 메세지 조회
	private final ParentRepository parentRepository;
	private final ChildRepository childRepository;
	private final NotificationRepository notificationRepository;
	
	// 메시지
    private static final String PARENT_NOT_FOUND = "해당 parentNum의 Parent가 없습니다.";
    private static final String CHILD_NOT_FOUND = "해당 childNum의 Child가 없습니다.";
	
    //타임아웃 설정 (1시간)
	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
	// Emitter를 관리하기 위한 Map
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
	
	// Service에 SSE Emitter를 생성하고 타임아웃을 설정 해 준다.
	private SseEmitter createEmitter(Long id) {
		System.out.println("createEmitter id: " + id);
		// 기존 Emiiter가 존재하면 삭제(중복방지)
		if(emitters.containsKey(id)) {
			emitters.remove(id); 
		}
		
		// 새 Emitter 생성
		SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
		emitters.put(id, emitter);
		
	    // Emitter 완료 시
	    emitter.onCompletion(() -> {
	        System.out.println("SSE Emitter 완료: " + id);
	        emitters.remove(id);
	    });

	    // 타임아웃 처리
	    emitter.onTimeout(() -> {
	        System.out.println("SSE Emitter 타임아웃: " + id);
	        emitters.remove(id);
	    });

	    // 에러 처리
	    emitter.onError((e) -> {
	        System.out.println("SSE Emitter 에러 발생: " + id + ", 에러: " + e.getMessage());
	        emitters.remove(id);
	    });
		
		return emitter;
	}
	
    // 알림 디비에 저장
	@Transactional
	public NotificationResponseDto sendEvent(NotificationRequestDto notiRequestDto) {
	    Parent parent = parentRepository.findById(notiRequestDto.getParentNum())
	            .orElseThrow(() -> new NoSuchElementException(PARENT_NOT_FOUND));
	    Child child = childRepository.findById(notiRequestDto.getChildNum())
	            .orElseThrow(() -> new NoSuchElementException(CHILD_NOT_FOUND));

	    // 알림 저장
	    Notification notification = new Notification(parent, child, notiRequestDto.getMessage(), notiRequestDto.getCategory(), notiRequestDto.getSenderType());
	    Notification saveNotification = notificationRepository.save(notification);
	    NotificationResponseDto notificationResponseDto = saveNotification.toNotification(saveNotification);
	    
//	    System.out.println("notificationResponseDto ==========" + notificationResponseDto);

	    // 발신자에 따라 SSE 전송 대상 결정.. 알림 전송 메서드 호출
	    if ("parent".equalsIgnoreCase(notiRequestDto.getSenderType())) {
//	    	System.out.println("아이에게 알림 전송");
	    	
	    	// sender가 부모라면 아이에게 알림 전송
	        sendSseNotification(child.getChildNum(), notificationResponseDto); 
	    } else {	    	
	    	// 아이가 부모에게 알림 전송
//	    	System.out.println("부모에게 알림 전송");
	        sendSseNotification(parent.getParentNum(), notificationResponseDto); 
	    }
	    
	    return notificationResponseDto;
		
	}
	
	// (https://velog.io/@black_han26/SSE-Server-Sent-Events)
	
	// SSE를 통해 실제 실시간 알림을 전송하는 메서드 
	private void sendSseNotification(Long id, NotificationResponseDto notificationResponseDto) {
	    SseEmitter emitter = emitters.get(id); // Emitter 가져오기
	    System.out.println("emitter 확인 : " + emitter);
	    
	    if (emitter == null) return; // Emitter 없으면 종료
	    
	    // Emitter가 존재하는 경우
	    try {
	        emitter.send(SseEmitter.event()
	                .id(String.valueOf(notificationResponseDto.getNotiNum()))
	                .data(new ObjectMapper().writeValueAsString(notificationResponseDto))
	                .reconnectTime(3000) // 클라이언트가 연결을 잃었을때 재연결 간격설정
	                );	        
	    } catch (IOException | IllegalStateException e) {
	    	emitter.completeWithError(e);
	        emitters.remove(id); // 오류 발생 시 Emitter 제거
	    }
	}

    
    // 클라이언트가 구독을 호출하는 메소드(클라이언트 controller에서 구독페이지 엔드포인트를 생성하는데 사용된다.)
    public SseEmitter subscribe(Long id, final HttpServletResponse response) {
        // 기존 Emitter가 있을 경우 삭제
        if (emitters.containsKey(id)) {
            emitters.remove(id);
        }
        // CORS 관련 헤더 설정
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");

        // SSE 관련 헤더 설정
        response.setContentType("text/event-stream; charset=UTF-8");
    	response.setCharacterEncoding("UTF-8");
        
        // id를 이용하여 클라이언트와 매핑되는 Emitter를 생성하고 저장함
        SseEmitter emitter = createEmitter(id); // Emitter 생성 및 추가
        emitters.put(id, emitter);
        
        // 클라이언트에게 주기적으로 빈 데이터를 보내 연결을 유지시킨다
        Runnable keepAlive = () -> {
            try {
                emitter.send(SseEmitter.event().name("keep-alive").data("ping").reconnectTime(3000));
            } catch (IOException e) {
//                e.printStackTrace(); // production 환경에서는 보안상 slf4j나 logback 등을 사용
            	logger.error("keep-aliv 메세지 전송실채, 클라이언트 연결 해제 처리. ", e);
            	emitters.remove(id); // 연결 끊김 시 Emitter 제거(추가)
            }
        };
        
        // 5초마다 빈 메시지를 보내는 스케줄러 실행
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(keepAlive, 0, 5, TimeUnit.SECONDS);
    	
    	// 초기 연결시 전송할 더미 데이터 생성
    	// 클라이언트와의 초기 연결에서 아무 이벤트도 전송하지 않으면, 재연결 요청이나 연결 자체에서 오류가 발생할 수 있기 때문에
    	// 첫 SSE 응답을 보낼시 더미 데이터를 넣어 오류를 방지한다.
//    	System.out.println("더미 데이터 전송 전 emitters 확인: " + emitters);
    	NotificationResponseDto dummyNotification  = new NotificationResponseDto();
    	dummyNotification.setNotiNum(-1L); //-1로 설정하여 더미임을 구분 가능
    	dummyNotification.setMessage("연결확인용 더미 데이터");
    	dummyNotification.setCategory("dummy");
    	dummyNotification.setParentNum(-1L);
    	dummyNotification.setChildNum(-1L);
    	dummyNotification.setSenderType("dummy");
    	
    	// 초기 더미 데이터를 전송하여 연결 상태 유지 
    	sendSseNotification(id, dummyNotification);
    	
    	return emitter;
    }
    // 실제 알림 전송 메서드
	public void notify(Long id, NotificationRequestDto notiRequestDto) {
		// 클라이언트에게 실제 알림데이터를 전송함
	    // NotificationRequestDto를 NotificationResponseDto로 변환
	    NotificationResponseDto notificationResponseDto = new NotificationResponseDto(
	        null,  // notiNum은 null로 시작, 서버에서 자동 생성됨
	        notiRequestDto.getParentNum(),
	        notiRequestDto.getChildNum(),
	        notiRequestDto.getMessage(),
	        notiRequestDto.getCategory(),
	        notiRequestDto.getSenderType(),
	        notiRequestDto.getIsRead()
	    );

		sendSseNotification(id, notificationResponseDto);
	}
    
    // 조회
    // 해당 child에 대한 모든 알림을 조회
    public List<Notification> getNotificationsForChild(Long childNum) {
    	
        return notificationRepository.getNotificationsForChild(childNum, "parent");
    }
    
    // 해당 parent에 대한 모든 알림을 조회
    public List<Notification> getNotificationsForParent(Long parentNum) {
        return notificationRepository.getNotificationsForParent(parentNum, "child");
    }
    
    // parent_num 반환
    public Long findParentNoByChildId (Long childNum) {
    	return notificationRepository.getParentNum(childNum);
    }

    // 알림 읽음 업데이트
    public void updateReadStatus(Long notiNum) {
    	notificationRepository.updateRead(notiNum);
    }
	
}
