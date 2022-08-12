package com.ssafy.banggawawo.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmotionDto {
    private double angry;     // 화남
    private double disgusted; // 역겨움
    private double fearful;   // 공포
    private double happy;     // 행복
    private double neutral;   // 보통
    private double sad;       // 슬픔
    private double surprised; // 놀람
}

