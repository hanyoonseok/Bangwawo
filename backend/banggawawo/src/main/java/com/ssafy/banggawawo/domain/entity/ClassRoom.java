package com.ssafy.banggawawo.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


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

    private String title;        //제목
    private String introduce;   //소개글
    private Integer maxcnt;     //최대인원수
    private Date stime;          //시작시간
    private Date etime;          //종료시간
    private Boolean opened;      //공개여부
    private String thumbnail;       //썸네일

    private Boolean state;       //상태  (대기, 끝)

    //classroom 생성시 필요f한 builder
    @Builder

    public ClassRoom(Long cId, Volunteer volunteer, List<Enrol> enrols, Request request, String title, String introduce, Integer maxcnt, Date stime, Date etime, Boolean opened, String thumbnail, Boolean state) {
        this.cId = cId;
        this.volunteer = volunteer;
        this.enrols = enrols;
        this.request = request;
        this.title = title;
        this.introduce = introduce;
        this.maxcnt = maxcnt;
        this.stime = stime;
        this.etime = etime;
        this.opened = opened;
        this.thumbnail = thumbnail;
        this.state = state;
    }

    //cid가 같으면 무조건 같은걸로 판단하자
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return cId.equals(classRoom.cId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(cId);
    }
}
