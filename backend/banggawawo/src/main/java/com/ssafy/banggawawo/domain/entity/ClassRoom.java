package com.ssafy.banggawawo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassRoom {
    @Id
    @GeneratedValue
    private Long c_id;          //수업 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="v_id")
    private Volunteer volunteer;//봉사자 아이디

    @OneToMany
    private List<Enrol> enrols; //수강신청목록

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "r_id")
    private Request request;

    private String c_vname;     //봉사자 이름
    private String c_title;     //제목
    private String c_introduce; //소개글
    private int c_maxcnt;       //최대인원수
    private Date c_stime;       //시작시간
    private Date c_etime;       //종료시간
    private boolean c_opened;   //공개여부
    private byte c_thumbnail;   //썸네일
    private boolean c_state;    //상태

}
