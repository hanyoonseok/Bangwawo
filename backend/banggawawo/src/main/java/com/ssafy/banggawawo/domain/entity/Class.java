package com.ssafy.banggawawo.domain.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@Entity
@Getter
public class Class {
    @Id
    @GeneratedValue
    private long c_id;          //수업 아이디
    private long v_id;          //봉사자 아이디
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
