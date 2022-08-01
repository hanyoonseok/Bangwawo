package com.ssafy.banggawawo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enrol {
    @Id
    @GeneratedValue
    private long e_id;            //신청 아이디
    private long s_id;            //학생 아이디
    private long c_id;            //수업 아이디
    private String e_feedback;    //봉사자 피드백
    private String e_emotion;     //감정 피드백(아직 자료형 미정)
    private String e_recording;   //녹화영상
}
