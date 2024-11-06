package com.web.spring.service;


import com.web.spring.dto.parent.ParentReportResponseDto;
import com.web.spring.entity.Child;
import com.web.spring.entity.Parent;
import com.web.spring.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ChildService childService;
    private final ParentRepository parentRepository;

    //부모가 자기 자식 찾기
    @Transactional
    public Child findParentChild(Long parentNum, Long childNum){
    	
    	System.out.println(parentNum +"," + childNum);

        Parent parent = parentRepository.findById(parentNum).orElseThrow();
        
        System.out.println(parent.toString() + " " + parent.getChildren().get(0));

        parent.getChildren().stream()
                .filter(c -> c.getChildNum().equals(childNum))
                .forEach(c -> System.out.println(c));
        
        
        return  parent.getChildren().stream()
							        .filter(c -> c.getChildNum().equals(childNum))
							        .findFirst()
                					.orElseThrow();
    }

    /* 월간 리포트 */
    @Transactional
    public ParentReportResponseDto getChildReport(Child child, String year, String month){


        //Chart1 - 카테고리 별 소비 현황
        HashMap<String, Integer> chartPayment = childService.showMonthChart(child.getChildNum(), Integer.parseInt(year), Integer.parseInt(month));

        //Chart1- 카테고리 별 계획
        HashMap<String, Integer> monthPlan =childService.monthPlan(child.getChildNum(), Integer.parseInt(year), Integer.parseInt(month));

        //Chart2 - Top3.
        LinkedHashMap<String, Integer> showQuizResultTop3= childService.showQuizResultTop3(child.getChildNum());

        //Chart2 - 방사형 교육 차트
        HashMap<String,Integer> showQuizResult =childService.showQuizResult(child.getChildNum());



        return new ParentReportResponseDto(chartPayment, monthPlan, showQuizResultTop3, showQuizResult);
    }


}
