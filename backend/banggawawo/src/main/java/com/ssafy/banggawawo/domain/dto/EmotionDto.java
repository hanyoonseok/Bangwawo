package com.ssafy.banggawawo.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmotionDto {
    private Double angry;     // 화남
    private Double disgusted; // 역겨움
    private Double fearful;   // 공포
    private Double happy;     // 행복
    private Double neutral;   // 보통
    private Double sad;       // 슬픔
    private Double surprised; // 놀람
}

