package com.ssafy.banggawawo.domain.dto;


import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import lombok.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public ClassDto setSearchfQuery(String title, Boolean opened, Boolean state){
        this.title = title;
        this.opened = opened;
        this.state = state;
        return this;
    }
}
