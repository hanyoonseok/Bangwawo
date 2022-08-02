package com.ssafy.banggawawo.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    @Id
    @GeneratedValue
    private Long s_id;          //학생 아이디

    @OneToMany(mappedBy = "student")
    private List<Enrol> enrols = new ArrayList<Enrol>(); //수강신청 목록 & 피드백 목록

    @OneToMany(mappedBy = "student")
    private List<Request> requests = new ArrayList<Request>(); //수업요청 리스트

    @OneToMany(mappedBy = "student")
    private List<SecritChat> secritChats = new ArrayList<SecritChat>();   //비밀친구 대화 리스트

    @OneToMany(mappedBy = "student")
    private List<Like> likes = new ArrayList<Like>();   //좋아요 리스트

    private String s_tonken;    //카카오 토큰
    private String s_name;      // 이름
    private int s_birth;        //출생년도
    private String s_img;         //프로필 사진
    private String s_nickname;  //벌명
    private String s_pemail;    //부모이메일
    private String s_ppw;       //부모 비밀번호

    @Embedded
    private Character s_character;//개인 3d캐릭터에 대한 정보

    @Builder
    public Student(Long s_id, String s_tonken, String s_name, int s_birth, String s_img,
                   String s_nickname, String s_pemail, String s_ppw, Character s_character){
        this.s_id = s_id;
        this.s_tonken = s_tonken;
        this.s_name = s_name;
        this.s_birth = s_birth;
        this.s_img = s_img;
        this.s_nickname = s_nickname;
        this.s_pemail = s_pemail;
        this.s_ppw = s_ppw;
        this.s_character = s_character;
    }


}
