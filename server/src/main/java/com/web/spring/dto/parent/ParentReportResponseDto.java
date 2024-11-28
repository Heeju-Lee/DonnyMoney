package com.web.spring.dto.parent;

import lombok.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentReportResponseDto {

    //실제 쓴 돈
    private LinkedHashMap<String, Integer> chartPayment;

    //소비 게획서에 쓴 돈
    private LinkedHashMap<String, Integer> monthPlan;

    // 퀴즈 많이 맞춘 순
    private HashMap<String, Integer> showQuizResultTop3;

    //퀴즈 결과
    private HashMap<String,Integer> showQuizResult;

}