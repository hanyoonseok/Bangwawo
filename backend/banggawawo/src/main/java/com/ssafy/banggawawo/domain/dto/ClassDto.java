package com.ssafy.banggawawo.domain.dto;


import java.util.Date;

public class ClassDto {
    private Long c_id;              // 수업id
    private Long v_id;              // 봉사자 id
    private String c_title;         // 제목
    private String c_introduce;     // 내용
    private Date c_stime;           // 시작시간
    private Date c_etime;           // 종료시간
    private int c_maxcnt;           // 최대 인원
    private boolean c_opened;       // 공개여부
    private String c_thumbnail;     // 썸네일
}
