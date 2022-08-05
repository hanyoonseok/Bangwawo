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

    @OneToMany(mappedBy = "volunteer")
    private List<ClassRoom> classRooms = new ArrayList<ClassRoom>();

    private String token;     // 카카오 토큰
    private String nickname;      // 별명
    private int ageRange;        // 출생년도
    private String introduce; // 자기소개

    @Embedded
    private Character character;//개인 3d캐릭터에대한 정보

    @Builder
    public Volunteer(Long vId, String token, String nickname,
                     int ageRange, String introduce, Character character){
        this.vId = vId;
        this.nickname = nickname;
        this.token = token;
        this.ageRange = ageRange;
        this.introduce = introduce;
        this.character = character;
    }
}