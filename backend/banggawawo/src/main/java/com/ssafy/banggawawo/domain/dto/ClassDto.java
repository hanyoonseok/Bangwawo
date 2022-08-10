package com.ssafy.banggawawo.domain.dto;


import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import lombok.*;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class ClassDto {
    private Long cId;             // 수업id
    private Volunteer vId;        // 봉사자
    private Request rId;          // 신청
    private String title;         // 제목
    private String introduce;     // 내용
    private LocalDateTime stime;           // 시작시간
    private LocalDateTime etime;           // 종료시간
    private Integer maxcnt;       // 최대 인원
    private Boolean opened;       // 공개여부
    private String thumbnail;     // 썸네일
    private Integer state;        // 상태 ( 0 : 대기중, 1 : 수업중 , 2 : 종료 )

    private String dateStr;     //수업 날짜
    private String stimeStr;    //수업 시작 시간
    private String etimeStr;    //수업 종료 시간

    public ClassDto setSearchfQuery(String title, Boolean opened, Integer state, Long vid){
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
                    LocalDateTime stime, LocalDateTime etime,
                    Integer maxcnt, Boolean opened,
                    String thumbnail, Integer state) {
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
        if(stime != null){
            this.dateStr = stime.toLocalDate().toString();
            this.stimeStr = stime.toLocalTime().toString();
        }
        if(etime != null){
            this.etimeStr = etime.toLocalTime().toString();
        }
    }
}
