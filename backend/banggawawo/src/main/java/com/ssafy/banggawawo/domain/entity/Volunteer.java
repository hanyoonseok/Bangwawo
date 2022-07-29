package com.ssafy.banggawawo.domain.entity;
import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Volunteer {
    @Id
    @GeneratedValue
    private long v_id;          // 봉사자 아이디
    private String v_token;     // 카카오 토큰
    private String v_name;      // 이름
    private int v_birth;        // 출생년도
    private byte v_img;         // 프로필 사진
    private String v_introduce; // 자기소개
}