package com.ssafy.banggawawo.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emotion {
    private Integer angry;     // 화남
    private Integer disgusted; // 역겨움
    private Integer fearful;   // 공포
    private Integer happy;     // 행복
    private Integer neutral;   // 보통
    private Integer sad;       // 슬픔
    private Integer surprised; // 놀람
}

