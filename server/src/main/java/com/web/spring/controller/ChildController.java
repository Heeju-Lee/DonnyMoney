package com.web.spring.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.web.spring.entity.Child;
import com.web.spring.entity.Parent;
import com.web.spring.entity.Payment;
import com.web.spring.entity.Quiz;
import com.web.spring.repository.ChildRepository;
import com.web.spring.repository.ParentRepository;
import com.web.spring.entity.Wish;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.web.spring.dto.child.ChildRequestDto;
import com.web.spring.dto.child.ChlidResponseDto;
import com.web.spring.dto.child.wish.WishRequestDto;
import com.web.spring.dto.child.wish.WishResponseDto;
import com.web.spring.dto.child.payment.PayRequestDto;

import com.web.spring.dto.child.plan.PlanRequestDto;
import com.web.spring.dto.child.plan.PlanResponseDto;
import com.web.spring.dto.child.point.PointRequestDto;
import com.web.spring.dto.child.quiz.QuizResponseDto;

import com.web.spring.service.ChildService;
import com.web.spring.service.ParentService;
import com.web.spring.service.WishService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChildController {

	private final ChildService childService;
	private final WishService wishService;
	
/* Child : 회원가입  --------------------------------------------------------------*/
	@PostMapping("/children")
	public ResponseEntity<?> singUp(@RequestBody ChildRequestDto childRequestDto){
		
		ChlidResponseDto response = childService.singUp(childRequestDto);
				
		return ResponseEntity.status(HttpStatus.CREATED)
				 			 .body(response);
	}


	
/* Plan : 소비 계획 세우기 --------------------------------------------------------------*/
	@PostMapping("/plans")
	public ResponseEntity<?> createPlan( @RequestBody PlanRequestDto planRequestDto){
		
		System.out.println(planRequestDto);
		
		PlanResponseDto response = childService.createPlan(planRequestDto);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(response);
	}
	

	@GetMapping("/plans")
	public ResponseEntity<?> showPlan(	@RequestParam  String year,
										@RequestParam  String month) throws Exception{
	
		PlanResponseDto response = childService.showPlan(year, month);
		System.out.println(response);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/plans/{planNum}")
	public ResponseEntity<?>updatePlan (@PathVariable String planNum,
										@RequestBody PlanRequestDto planRequestDto) throws Exception{
		
		PlanResponseDto plan = childService.updatePlan(Long.parseLong(planNum), planRequestDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(plan);	
	}

	
/* MGMT  --------------------------------------------------------------*/

	//이번달 소비리스트
	@GetMapping("/payments/{childNum}")
	public ResponseEntity<?> showMonthList(	@PathVariable String childNum,
											@RequestParam  String year,
											@RequestParam  String month){


		List<Payment> response = childService.showMonthList(Long.parseLong(childNum), Integer.parseInt(year), Integer.parseInt(month));
		System.out.println(response);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	//이번달 소비리스트
	@GetMapping("/payments/chart/{childNum}")
	public ResponseEntity<?> showMonthChart(	@PathVariable String childNum,
												@RequestParam  String year,
												@RequestParam  String month){

		HashMap<String, Integer> response = childService.showMonthChart(Long.valueOf(childNum), Integer.parseInt(year), Integer.parseInt(month));
		System.out.println(response);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/*이달의 계획 내역 도넛차트*/
	@GetMapping("/plan/chart/{childNum}")
	public ResponseEntity<?> monthPlan(	@PathVariable String childNum,
										@RequestParam String year,
									   	@RequestParam String month) {

		HashMap<String, Integer> response = childService.monthPlan(Long.valueOf(childNum) ,Integer.parseInt(year), Integer.parseInt(month));

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	//포인트 조회
	@GetMapping("/point/{childNum}")
	public ResponseEntity<?> showPoint(@PathVariable Long childNum){

		int response = childService.showPoint(childNum);
		return ResponseEntity.status(HttpStatus.OK)
							.body(response);
	}
	
	//포인트 업데이트
	@PutMapping("/point")
	public ResponseEntity<?> updatePoint(@RequestBody PointRequestDto request){

		int response = childService.updatePoint(request.getChildNum(), request.getPoint());
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	
/* WISH -------------------------------------------------------------------------*/
	//위시 등록하기
	@PostMapping("/wishes")
	public ResponseEntity<?> createWish(@RequestBody WishRequestDto wishRequestDto){
		
		childService.createWish(wishRequestDto);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				 			 .body("info :: createWish Success");
	}	
	
	//위시 전체리스트 조회(Active)
	@GetMapping("/wishes/active/{childNum}")
	public ResponseEntity<?> showActiveWishList(@PathVariable String childNum){
		
		List<Wish> wishList = childService.showActiveWishList(childNum);
		
		return ResponseEntity.status(HttpStatus.OK)
				 			 .body(wishList);
	}
	
	//위시 전체리스트 조회(Finished)
	@GetMapping("/wishes/finished/{childNum}")
	public ResponseEntity<?> showFinishedWishList(@PathVariable String childNum){
		
		List<Wish> wishList = childService.showFinishedWishList(childNum);
		
		return ResponseEntity.status(HttpStatus.OK)
				 			 .body(wishList);
	}
	
	//위시 상세보기
	@GetMapping("/wishes/{wishNum}")
	public ResponseEntity<?> showWishDetail(@PathVariable String wishNum){
		WishResponseDto response = childService.showWishDetail(wishNum);
		
		return ResponseEntity.status(HttpStatus.OK)
	 			 .body(response);
	}	
	
	//위시 돈모으기 
	@PutMapping("/wishes")
	public ResponseEntity<?> savingWish(@RequestParam String wishNum,
										@RequestParam String savingAmt){
		
		childService.savingWish(wishNum, savingAmt);
		
		return ResponseEntity.status(HttpStatus.OK)
				 			 .body("info :: savingWish Success");
	}
	
	//위시 삭제하기
	@DeleteMapping("/wishes/{wishNum}")
	public ResponseEntity<?> deleteWish(@PathVariable String wishNum){
		childService.deleteWish(wishNum);
		
		return ResponseEntity.status(HttpStatus.OK)
	 			 .body("info :: deleteWish Success");
	}

	
/* Quiz : 퀴즈 ----------------------------------------------------------------------------*/	
	/*아이의 퀴즈 결과 보여주기	*/
	@GetMapping("/quiz/{childNum}")
	public ResponseEntity<?> showQuizResult( @PathVariable Long childNum){

		HashMap<String, Integer> response = childService.showQuizResult(childNum);

		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}
	
	
	/*아이의 퀴즈 결과를 top3로 나눠서 보여주기*/
	@GetMapping("quiz/top3/{childNum}")
	public ResponseEntity<?> showQuizResultTop3(@PathVariable Long childNum){
		
		HashMap<String, Integer> response = childService.showQuizResultTop3(childNum);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(response);
	}
	
	
	//퀴즈 문제 내기 (각 카테고리 별 문제 1개씩 랜덤)
	@GetMapping("/quiz")
	public ResponseEntity<?> showQuiz() {
		List<Quiz> quizList = childService.showQuiz();
		
		return ResponseEntity.ok(quizList);
	}
	
	//퀴즈 업데이트
	@PutMapping("/quiz/{childNum}")
	public ResponseEntity<?> updateScore(@PathVariable Long childNum,
										 @RequestBody List<QuizResponseDto> quizResponse){
		// 헤당 childNum 에 퀴즈 업데이트
		childService.updateQuiz(childNum, quizResponse);
		
		return ResponseEntity.ok(HttpStatus.OK);
  }
	
	
}
