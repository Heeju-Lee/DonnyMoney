package com.web.spring.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.web.spring.entity.Parent;
import com.web.spring.entity.Payment;
import com.web.spring.repository.ParentRepository;

import org.springframework.scheduling.support.ScheduledTaskObservationDocumentation.LowCardinalityKeyNames;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.spring.dto.child.ChildRequestDto;
import com.web.spring.dto.child.ChlidResponseDto;
import com.web.spring.dto.child.plan.PlanRequestDto;
import com.web.spring.dto.child.plan.PlanResponseDto;
import com.web.spring.dto.child.quiz.QuizResponseDto;
import com.web.spring.dto.child.wish.WishRequestDto;
import com.web.spring.dto.child.wish.WishResponseDto;
import com.web.spring.entity.Child;

import com.web.spring.entity.Payment;
import com.web.spring.entity.Plan;
import com.web.spring.entity.Wish;
import com.web.spring.entity.Plan;
import com.web.spring.entity.Quiz;
import com.web.spring.exception.ExceededAmountException;
import com.web.spring.exception.NotEnoughPointsException;

import com.web.spring.repository.ChildRepository;
import com.web.spring.repository.PlanRepository;
import com.web.spring.repository.WishRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChildService {

	private final ChildRepository childRepository;
	private final PlanRepository planRepository;
	private final ParentRepository parentRepository;
	private final WishRepository wishRepository;
	
/* Child : 회원가입 */	
	@Transactional
	public ChlidResponseDto singUp(ChildRequestDto childRequestDto) {
		
		Child child = childRequestDto.toChild(childRequestDto);

		Long parentNum =childRequestDto.getParentNum();
		System.out.println(parentNum);
		Parent parent = parentRepository.findById(parentNum).orElseThrow();

		//아이게 부모 저장
		child.setParent(parent);
		Child rChild = childRepository.save(child);
		
		//부모에게 아이 저장
		parent.getChildren().add(rChild);
		
		System.out.println("rChild : " + rChild);
		
		return new ChlidResponseDto(rChild);
				
	}
	
	@Transactional(readOnly = true)
	public String duplicateCheck(String id) {
		
		Child rMember = childRepository.duplicateCheck(id);
		System.out.println( "rMember ==> " +  rMember);
		
		if(rMember == null) return "아이디 사용 가능";
		else return "아이디 사용 불가";
		
	}
	

	@Transactional
	public Child findChild(Long childNum){

		Child child = childRepository.findById(childNum)
				  					 .orElseThrow( () -> new NoSuchElementException("Child with cNum not found"));

		return child;
	}


/* Plan : 소비 계획 세우기 */
	@Transactional
	public PlanResponseDto createPlan(PlanRequestDto planRequestDto ){
		
		//1. client에서 c_num 넣어주는 방법 -> PlanRequestDto 사용
		//2. JWT 토큰 까서 c_num 확인하는 방법
		//c_num 받았다고 치고.
		Child child = findChild(planRequestDto.getChildNum());
		System.out.println(child);
		
		Plan plan = planRequestDto.toPlan(planRequestDto);
		
		planRepository.save(plan);
		child.getPlans().add(plan);

		//목업 데이터 저장
		List<Payment> payments = childRepository.showMonthPayments(child.getChildNum());
		child.setPayments(payments);
		
		return new PlanResponseDto(plan);
	}
	
	//소비 계획 조회하기
	@Transactional
	public PlanResponseDto showPlan(String year, String month) throws Exception{
	
		//토큰 까서 childNum 받았다 치고,
		Child child =findChild(1L);
		System.out.println(child);
		
		Plan plan = childRepository.findPlanByDate(Integer.parseInt(year), Integer.parseInt(month) );
		
		return new PlanResponseDto(plan);
	}
	
	
	//소비 계획 수정하기
	@Transactional
	public PlanResponseDto updatePlan(Long planNum, PlanRequestDto planRequestDto) throws Exception {
		
		System.out.println(planNum);
		
		Plan plan = planRepository.findById(planNum)
								  .orElseThrow( () -> new NoSuchElementException("PlanNum not found"));
		
		System.out.println(plan);
		plan.setShopping(planRequestDto.getShopping());
		plan.setCvs(planRequestDto.getCvs());
		plan.setFood(planRequestDto.getFood());
		plan.setOthers(planRequestDto.getOthers());
		plan.setSaving(planRequestDto.getSaving());
		plan.setTransport(planRequestDto.getTransport());
		
		return new PlanResponseDto(plan);
	}
	

	// 포인트 조회
	@Transactional
	public int showPoint(Long childNum) {
		return childRepository.showPoint(childNum)
				.orElseThrow(() -> new NotEnoughPointsException("포인트 값이 존재하지 않습니다."));
	}
	
	// 포인트 업데이트
	@Transactional
	public int updatePoint(Long childNum, int point) {
		// 기존 포인트 잔액 조회
		Optional<Integer> curPoint = childRepository.showPoint(childNum);
	    // 기존 포인트 존재유무 체크
		if (!curPoint.isPresent()) {
			throw new NotEnoughPointsException("포인트가 존재하지 않습니다.");
		}
		
		int updatedPoint = curPoint.get() + point;
		
		// 차감후 포인트 음수인 경우
		if(updatedPoint < 0) {
			throw new NotEnoughPointsException("포인트가 부족합니다.");
		}
		
		// 포인트 업데이트
		childRepository.updatePoint(curPoint.get() + point, childNum);
		
		// 변경된 포인트 반환 
		return updatedPoint;			
	}

	// 이번달 소비내역
	@Transactional(readOnly = true)
	public List<Payment> showMonthList(Long childNum, int year, int month) {

		Child child = findChild(childNum);
		List<Payment> payments = child.getPayments();
		List<Payment> monthPayment = new ArrayList<>();

		payments.forEach( payment -> {

			LocalDate date = payment.getCreatedAt();
			if( date.getMonthValue() == month && date.getYear() == year){
				monthPayment.add(payment);
			}
		});

		return monthPayment;
	}
	
	// 이번달 카테고리별 소비내역
	@Transactional(readOnly = true)
	public HashMap<String, Integer> showMonthChart(Long childNum, int year, int month) {
	    List<Payment> payments = showMonthList(childNum, year, month);
	    
	    HashMap<String, Integer> categoryTotal = new HashMap<>();

	    payments.forEach(payment -> {
	        String category = payment.getCategory();
	        int amount = payment.getPaymentAmt();
	        
	        categoryTotal.put(category, categoryTotal.getOrDefault(category, 0) + amount);
	    });

	    return categoryTotal;
	}
	
	// 퀴즈문제 보여주기
	@Transactional(readOnly = true)
	public List<Quiz> showQuiz() {	
		// 카테고리 리스트 생성
        List<String> categories = Arrays.asList("history", "investment", "government", "exchangeRate", "word");
        List<Quiz> quizList = new ArrayList<>();

        for (String category : categories) {
            List<Quiz> result = childRepository.findQuizByCategoryRandom(category);
            // 결과가 비어있지 않은 경우 첫 번째 퀴즈를 선택하여 리스트에 추
            if (!result.isEmpty()) {
            	quizList.add(result.get(0));
            }
        }

        return quizList;
    }
	
	// 퀴즈 업데이트
	/* 
	 * 과정
	 * 문제를 클라이언트에 전달
	 * 유저가 문제를 풀때마다 문제 정답과 비교하여 QuizResponseDto 형태로 넣음 (클라이언트 작업)
	 * 문제 완료후 list<QuizResponseDto> 를 받아 updateQuiz 진행
	 */
	@Transactional
	public void updateQuiz(Long childNum, List<QuizResponseDto> quizResponse) {
		Child child = childRepository.findById(childNum).orElseThrow();
		
		// 각 퀴즈 답 확인해서 점수 업데이트 함
		for(QuizResponseDto response: quizResponse) {
			if(response.getScore() == 1) { //문제 맞춘 경우
                switch (response.getCategory()) {
                case "history":
                    child.setQHistory(child.getQHistory() + 1);
                    System.out.println("history");
                    break;
                case "investment":
                    child.setQInvestment(child.getQInvestment() + 1);
                    break;
                case "government":
                    child.setQGoverment(child.getQGoverment() + 1);
                    break;
                case "word":
                    child.setQWord(child.getQWord() + 1);
                    break;
                case "exchangeRate":
                    child.setQExchangeRate(child.getQExchangeRate() + 1);
                    break;
                default:
                    break;
                }
			}
		}
		
		childRepository.save(child);
  }
 
	//이번달 계획 차트
	@Transactional(readOnly = true)
	public HashMap<String, Integer> monthPlan(Long childNum, int year, int month){

		HashMap<String, Integer> response = new HashMap<>();
		Child child = findChild(childNum);

		Plan monthPlan = child.getPlans().stream()
						.filter(plan -> plan.getModifiedAt().getMonthValue() == month && plan.getModifiedAt().getYear() == year )
						.findFirst()
						.orElseThrow();

		response.put("shopping", monthPlan.getShopping());
		response.put("food", monthPlan.getFood());
		response.put("transport", monthPlan.getTransport());
		response.put("cvs", monthPlan.getCvs());
		response.put("saving", monthPlan.getSaving());
		response.put("others", monthPlan.getOthers());
		
		return response;
	}
	
	//퀴즈 결과 확인하기
	@Transactional(readOnly = true)
	public  HashMap<String, Integer> showQuizResult(Long child){
		HashMap<String, Integer> result = new HashMap<>();
		
		Child qr= childRepository.findById(child).get();
		
		//환율결과 넣기
		result.put("qExchangeRate", qr.getQExchangeRate());
		//투자 결과 넣기
		result.put("qInvestment", qr.getQInvestment());
		//경제용어결과 넣기
		result.put("qWord", qr.getQWord());
		//정부결과 넣기
		result.put("qGoverment", qr.getQGoverment());
		//경제의 역사 결과 넣기
		result.put("qHistory", qr.getQHistory());
		
		return result;
	}
	
	//퀴즈결과 Top3만 보기
	public LinkedHashMap<String,Integer> showQuizResultTop3(Long child){

		HashMap<String, Integer> result = new HashMap<>();
		
		Child qr= childRepository.findById(child).get();
		
		//환율결과 넣기
		result.put("qExchangeRate", qr.getQExchangeRate());
		//투자 결과 넣기
		result.put("qInvestment", qr.getQInvestment());
		//경제용어결과 넣기
		result.put("qWord", qr.getQWord());
		//정부결과 넣기
		result.put("qGoverment", qr.getQGoverment());
		//경제의 역사 결과 넣기
		result.put("qHistory", qr.getQHistory());

		LinkedHashMap<String, Integer> sortedResult = result.entrySet()
			    .stream()
			    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) // 값 내림차순 정렬
			    .limit(3) // 상위 3개만 선택
			    .collect(Collectors.toMap(
			        Map.Entry::getKey,
			        Map.Entry::getValue,
			        (e1, e2) -> e1, // 중복 키가 발생할 경우 해결 방법 (여기서는 첫 번째 값 유지)
			        LinkedHashMap::new // 순서를 유지하기 위해 LinkedHashMap 사용
			    ));
		
		
		return sortedResult;
	}
	
	/* WISH METHOD */
	
	// Wish : 위시 등록하기
	@Transactional
	public void createWish(WishRequestDto wishRequestDto) {
		
		Child child =findChild(1L);
		Wish wish = wishRequestDto.toWish(wishRequestDto);
		System.out.println("Createwish ::"+wish);
		
		wishRepository.save(wish);
		child.getWishes().add(wish);

	}

	// Wish : Active 위시 전체 조회 
	@Transactional
	public List<Wish> showActiveWishList(String childNum) {

		List<Wish> wishList = childRepository.showActiveWishList(Long.parseLong(childNum));
		wishList.forEach(c->System.out.println("showActiveWish :: "+c));
		
		return wishList;
	}
	
	// Wish : finish 위시 전체 조회 
	@Transactional
	public List<Wish> showFinishedWishList(String childNum) {


		List<Wish> wishList = childRepository.showFinishedWishList(Long.parseLong(childNum));
		wishList.forEach(c->System.out.println("showFinishedWish :: "+c));
		
		return wishList;
	}
	
	// Wish : 위시 상세보기 
	@Transactional
	public WishResponseDto showWishDetail(String wishNum) {
		
		Child child =findChild(1L);
		Wish wish = wishRepository.findById(Long.parseLong(wishNum))
				  .orElseThrow(() -> new NoSuchElementException("Wish with wishNum " + wishNum + " not found"));
		System.out.println("showWishDetail :: "+wish);
		
		return new WishResponseDto(wish);
	}
	
	// Wish : 위시 돈모으기
	@Transactional
	public void savingWish(String wishNum, String savingAmt) {
		
		// 인자값 미리 파싱
		Long parseWishNum = Long.parseLong(wishNum);
		int parseSavingAmt = Integer.parseInt(savingAmt);
		int savingResult = 0;
		
		//토큰에 있는 아이디
		Child child =findChild(1L);
		// children 변경전 포인트
		System.out.println("beforeSaving_ChildPoint :: "+child.getPoint());
		
		//해당하는 위시 가져오기
		Wish wish = wishRepository.findById(Long.parseLong(wishNum))
				  .orElseThrow(() -> new NoSuchElementException("Wish with wishNum " + wishNum + " not found"));
		int totalSaving = wish.getSavingAmt() + parseSavingAmt;
		int wishPrice = wish.getPrice();
		
		// wish 가격과 totalSaving 이 같다면 -> isFinish == True
		if(wishPrice == totalSaving) {
			// 변경 완료 여부 확인
			savingResult = wishRepository.savingWish(parseWishNum, totalSaving);
			wishRepository.isFinish(parseWishNum, true);
		}else if (wishPrice >= totalSaving) {
			// 변경 완료 여부 확인
			savingResult = wishRepository.savingWish(parseWishNum, totalSaving);
		}else {
			throw new ExceededAmountException("모으려는 금액이 price 보다 많습니다.");
		}
		
		// 포인트 변경
		System.out.println(child.getChildNum());
		int pointResult = updatePoint(child.getChildNum(), -parseSavingAmt);
		
		System.out.println("afterSavingWish :: complete ->"+ savingResult );
	}

	
	// Wish : 위시 삭제하기 + savingPoint return(updatePoint 호출)
	@Transactional
	public void deleteWish(String wishNum) {
		
		// 인자값 미리 파싱
		Long parseWishNum = Long.parseLong(wishNum);
		
		//토큰에 있는 아이디
		Child child =findChild(1L);
		// children 변경전 포인트
		int curPoint = child.getPoint();
		System.out.println("beforeDeleteWish_Child :: "+curPoint);
		
		//해당하는 위시 가져오기
		Wish wish = wishRepository.findById(Long.parseLong(wishNum))
				  .orElseThrow(() -> new NoSuchElementException("Wish with wishNum " + wishNum + " not found"));
		// 현재까지 모은 금액
		int curSaving = wish.getSavingAmt();
		
		// 변경된 포인트
		int pointResult = updatePoint(child.getChildNum(), curSaving);
		
		// 변경 완료 여부 확인
		wishRepository.deleteById(parseWishNum);
		
		System.out.println("afterDeleteWish :: complete ->"+ findChild(1L) );
	}
}
