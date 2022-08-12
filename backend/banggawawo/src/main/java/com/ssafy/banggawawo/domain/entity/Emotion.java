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
    private int angry;     // 화남
    private int disgusted; // 역겨움
    private int fearful;   // 공포
    private int happy;     // 행복
    private int neutral;   // 보통
    private int sad;       // 슬픔
    private int surprised; // 놀람
}

