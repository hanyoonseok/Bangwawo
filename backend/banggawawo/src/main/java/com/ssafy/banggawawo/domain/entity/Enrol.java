package com.ssafy.banggawawo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enrol {
    @Id
    @GeneratedValue
    private Long e_id;            //신청 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="s_id")
    private Student student;      //학생

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="c_id")
    private ClassRoom class_room;            //수업

    private String e_feedback;    //봉사자 피드백
    private String e_emotion;     //감정 피드백(아직 자료형 미정)
    private String e_recording;   //녹화영상
}
