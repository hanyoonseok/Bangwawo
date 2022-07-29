package com.ssafy.banggawawo.domain.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
public class Request {
    @Id
    @GeneratedValue
    private long r_id;          // 요청 아이디
    private long s_id;          // 학생 아이디
    private long c_id;          // 수업 아이디
    private long v_id;          // 봉사자 아이디
    private String r_title;     // 제목
    private String r_content;   // 내용
    private boolean r_solved;   // 해결여부
    private int r_like;         // 좋아요수
    private int r_count;        // 조회수

}
