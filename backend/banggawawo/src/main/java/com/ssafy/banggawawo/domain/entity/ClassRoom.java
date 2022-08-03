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
    private Long c_id;          //수업 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="v_id")
    private Volunteer volunteer;//봉사자 아이디

    @OneToMany
    private List<Enrol> enrols = new ArrayList<Enrol>(); //수강신청목록

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
    private String c_thumbnail;   //썸네일
    private boolean c_state;    //상태

    @Builder
    public ClassRoom(Long c_id,
                     Volunteer volunteer,
                     Request request,
                     String c_vname,
                     String c_title,
                     String c_introduce,
                     int c_maxcnt,
                     Date c_stime, Date c_etime,
                     boolean c_opened,
                     String c_thumbnail) {
        this.c_id = c_id;
        this.volunteer = volunteer;
        this.request = request;
        this.c_vname = c_vname;
        this.c_title = c_title;
        this.c_introduce = c_introduce;
        this.c_maxcnt = c_maxcnt;
        this.c_stime = c_stime;
        this.c_etime = c_etime;
        this.c_opened = c_opened;
        this.c_thumbnail = c_thumbnail;
    }
}
