package com.ssafy.banggawawo.domain.entity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Volunteer {
    @Id
    @GeneratedValue
    private Long vId;          // 봉사자 아이디

//    @OneToMany(mappedBy = "volunteer", fetch = FetchType.EAGER) // proxy 객체가 담겨서 반환되지 않도록 Eager로 설정
//    private List<ClassRoom> classRooms = new ArrayList<ClassRoom>();

    private String kakaoId;     // 카카오 회원번호
    private String nickname;      // 별명
    private int ageRange;        // 출생년도
    private String introduce; // 자기소개

    @Embedded
    private Character character;//개인 3d캐릭터에 대한 정보

    @Builder
    public Volunteer(Long vId, String kakaoId, String nickname,
                     int ageRange, String introduce, Character character){
        this.vId = vId;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
        this.ageRange = ageRange;
        this.introduce = introduce;
        this.character = character;
    }
}