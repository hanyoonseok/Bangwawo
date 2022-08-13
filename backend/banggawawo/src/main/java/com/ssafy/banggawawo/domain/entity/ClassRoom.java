package com.ssafy.banggawawo.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="classes")
public class ClassRoom {
    @Id
    @GeneratedValue
    private Long cId;          //수업 아이디

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="vId")
    private Volunteer volunteer;//봉사자

    //@OneToMany(mappedBy="classes")
    //private List<Enrol> enrols = new ArrayList<Enrol>(); //수강신청목록

    private Long rId;

    private String title;        //제목
    private String introduce;   //소개글
    private Integer maxcnt;     //최대인원수

    private LocalDateTime stime;          //시작시간
    private LocalDateTime etime;          //종료시간
    private Boolean opened;      //공개여부

    @Column(columnDefinition = "LONGTEXT")
    private String thumbnail;       //썸네일

    @ColumnDefault("0")
    private Integer state;       //상태  (0 : 대기, 1 : 수업중, 2 : 종료)

    //classroom 생성시 필요한 builder
    @Builder
    public ClassRoom(Long cId,
                     Volunteer volunteer,
                     //List<Enrol> enrols,
                     Long rId,
                     String title, String introduce,
                     Integer maxcnt, LocalDateTime stime, LocalDateTime etime,
                     Boolean opened, String thumbnail, Integer state) {
        this.cId = cId;
        this.volunteer = volunteer;
        //this.enrols = enrols;
        this.rId = rId;
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
