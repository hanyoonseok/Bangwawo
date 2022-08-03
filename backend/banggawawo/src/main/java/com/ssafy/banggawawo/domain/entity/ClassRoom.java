package com.ssafy.banggawawo.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="class_room")
public class ClassRoom {
    @Id
    @GeneratedValue
    private Long cId;          //수업 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vId")
    private Volunteer volunteer;//봉사자

    @OneToMany
    private List<Enrol> enrols = new ArrayList<Enrol>(); //수강신청목록

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rId")
    private Request request;

    private String title;     //제목
    private String introduce; //소개글
    private Integer maxcnt;       //최대인원수
    private Date stime;       //시작시간
    private Date etime;       //종료시간
    private Boolean opened;   //공개여부
    private String thumbnail;   //썸네일
    private Boolean state;    //상태

    //classroom 생성시 필요한 builder
    //여기에 c_id, state만 들어가면 수정용
    @Builder
    public ClassRoom(Volunteer volunteer, Request request,
                     String title, String introduce,
                     Integer maxcnt, Date stime, Date etime,
                     Boolean opened, String thumbnail) {
        this.volunteer = volunteer;
        this.request = request;
        this.title = title;
        this.introduce = introduce;
        this.maxcnt = maxcnt;
        this.stime = stime;
        this.etime = etime;
        this.opened = opened;
        this.thumbnail = thumbnail;
    }

    public void setCId(Long c_id) {
        this.cId = c_id;
    }
    public void setState(Boolean state){
        this.state = state;
    }
}
