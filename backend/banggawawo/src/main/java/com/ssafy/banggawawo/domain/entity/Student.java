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
    private Long sId;          //학생 아이디

    @OneToMany(mappedBy = "student")
    private List<Enrol> enrols = new ArrayList<Enrol>(); //수강신청 목록 & 피드백 목록

    @OneToMany(mappedBy = "student")
    private List<Request> requests = new ArrayList<Request>(); //수업요청 리스트

    @OneToMany(mappedBy = "student")
    private List<SecritChat> secritChats = new ArrayList<SecritChat>();   //비밀친구 대화 리스트

    @OneToMany(mappedBy = "student")
    private List<Like> likes = new ArrayList<Like>();   //좋아요 리스트

    private String token;       //카카오 토큰
    private String name;      //이름
    private int birth;        //출생년도
    private String nickname;    //별명
    private String pemail;   //부모이메일
    private String ppw;       //부모 비밀번호

    @Embedded
    private Character character;//개인 3d캐릭터에 대한 정보

    @Builder
        public Student(String token, String name, int birth,
                       String nickname, String pemail, String ppw, Character character){
        this.token = token;
        this.name = name;
        this.birth = birth;
        this.nickname = nickname;
        this.pemail = pemail;
        this.ppw = ppw;
        this.character = character;
    }


}
