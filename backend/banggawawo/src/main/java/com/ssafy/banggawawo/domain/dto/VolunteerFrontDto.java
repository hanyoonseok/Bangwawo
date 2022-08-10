package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Character;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VolunteerFrontDto {
    private Long vId;          // 봉사자 아이디
    private String kakaoId;     // 카카오 회원번호
    private String nickname;      // 별명
    private int ageRange;        // 연령대
    private String introduce; // 자기소개
    private boolean talkable;   // 대화 가능 여부
    private ColorDto[] character;//개인 3d 캐릭터에 대한 정보

    public VolunteerFrontDto() {}

    public VolunteerFrontDto(VolunteerDto volunteer){
        this.vId = volunteer.getVId();
        this.kakaoId = volunteer.getKakaoId();
        this.nickname = volunteer.getNickname();
        this.ageRange = volunteer.getAgeRange();
        this.introduce = volunteer.getIntroduce();
        this.talkable = volunteer.isTalkable();
        this.character = volunteer.toArray(volunteer.getCharacter());
    }

    public Character toClass(ColorDto[] arr){
        return new Character(arr[0].getColor(), arr[1].getColor(), arr[2].getColor(),
                arr[3].getColor(), arr[4].getColor(), arr[5].getColor());
    }

}
