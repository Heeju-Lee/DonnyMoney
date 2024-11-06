package com.web.spring.dto.child.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizResponseDto {
    private int score; // 채점한 결과 1 or 0로 가져옴
    private String category;
    private int quizNum;
}
