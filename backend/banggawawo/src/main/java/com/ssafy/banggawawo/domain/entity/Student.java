package com.ssafy.banggawawo.domain.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Student {
    @Id
    @GeneratedValue
    private Long s_id;          //학생 아이디
    private String s_tonken;    //카카오 토큰
    private String s_name;      // 이름
    private int s_birth;        //출생년도
    private byte s_img;         //프로필 사진
    private String s_nickname;  //벌명
    private String s_pemail;    //부모이메일
    private String s_ppw;       //부모 비밀번호
    private String s_3d;        //3D 캐릭터



}
