package com.ssafy.banggawawo.domain.entity;

import com.ssafy.banggawawo.domain.dto.ColorDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    @Id
    @GeneratedValue
    private Long sId;          //학생 아이디

    private String kakaoId;       //카카오 회원번호
    private int ageRange;        //연령대
    private String nickname;    //별명
    private String pemail;   //부모이메일
    private String ppw;       //부모 비밀번호

    @Embedded
    private Character character;//개인 3d캐릭터에 대한 정보

    @Builder
    public Student(Long sId, String kakaoId, int ageRange,
                    String nickname, String pemail, String ppw, Character character){
        this.sId = sId;
        this.kakaoId = kakaoId;
        this.ageRange = ageRange;
        this.nickname = nickname;
        this.pemail = pemail;
        this.ppw = ppw;
        this.character = character;
    }


}
