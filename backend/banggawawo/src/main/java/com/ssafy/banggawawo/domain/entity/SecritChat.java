package com.ssafy.banggawawo.domain.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@Entity
@Getter
public class SecritChat {
    @Id
    @GeneratedValue
    private long sc_id;           //비밀친구 아이디
    private long s_id;            //학생 아이디
    private String sc_content;    //위험 내용
    private Date sc_date;         //날짜
}
