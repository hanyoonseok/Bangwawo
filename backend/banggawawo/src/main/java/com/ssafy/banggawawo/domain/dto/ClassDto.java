package com.ssafy.banggawawo.domain.dto;


import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import lombok.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class ClassDto {
    private Long cId;             // 수업id
    private Volunteer vId;        // 봉사자
    private Request rId;          // 신청
    private String title;         // 제목
    private String introduce;     // 내용
    private Date stime;           // 시작시간
    private Date etime;           // 종료시간
    private Integer maxcnt;       // 최대 인원
    private Boolean opened;       // 공개여부
    private String thumbnail;     // 썸네일
    private Boolean state;        // 상태

    public ClassDto setSearchfQuery(String title, Boolean opened, Boolean state, Long vid){
        this.title = title;
        this.opened = opened;
        this.state = state;
        this.vId = Volunteer.builder().vId(vid).build();
        return this;
    }

    @Builder
    public ClassDto(Long cId,
                    Volunteer vId,
                    Request rId,
                    String title, String introduce,
                    Date stime, Date etime,
                    Integer maxcnt, Boolean opened,
                    String thumbnail, Boolean state) {
        this.cId = cId;
        this.vId = vId;
        this.rId = rId;
        this.title = title;
        this.introduce = introduce;
        this.stime = stime;
        this.etime = etime;
        this.maxcnt = maxcnt;
        this.opened = opened;
        this.thumbnail = thumbnail;
        this.state = state;
    }
}
