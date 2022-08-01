package com.ssafy.banggawawo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    @Id
    @GeneratedValue
    private Long s_id;          //학생 아이디

    @OneToMany


    private String s_tonken;    //카카오 토큰
    private String s_name;      // 이름
    private int s_birth;        //출생년도
    private byte s_img;         //프로필 사진
    private String s_nickname;  //벌명
    private String s_pemail;    //부모이메일
    private String s_ppw;       //부모 비밀번호

    @Embedded
    private Character character;//개인 3d캐릭터에대한 정보



}
