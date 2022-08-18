package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Character;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VolunteerDto {
    private Long vId;          // 봉사자 아이디
    private String kakaoId;     // 카카오 회원번호
    private String nickname;      // 별명
    private int ageRange;        // 연령대
    private String introduce; // 자기소개
    private boolean talkable;   // 대화 가능 여부
    private Character character;//개인 3d 캐릭터에 대한 정보

    public VolunteerDto (Volunteer volunteer){
        this.vId = volunteer.getVId();
        this.kakaoId = volunteer.getKakaoId();
        this.nickname = volunteer.getNickname();
        this.ageRange = volunteer.getAgeRange();
        this.introduce = volunteer.getIntroduce();
        this.talkable = volunteer.isTalkable();
        this.character = volunteer.getCharacter();
    }
    public VolunteerDto (VolunteerFrontDto volunteer){
        this.vId = volunteer.getVId();
        this.kakaoId = volunteer.getKakaoId();
        this.nickname = volunteer.getNickname();
        this.ageRange = volunteer.getAgeRange();
        this.introduce = volunteer.getIntroduce();
        this.talkable = volunteer.isTalkable();
        this.character = volunteer.toClass(volunteer.getCharacter());
    }

    ColorDto[] toArray(Character chr){
        ColorDto[] list = new ColorDto[6];
        list[0] = new ColorDto("body", chr.getBody());
        list[1] = new ColorDto("foot", chr.getFoot());
        list[2] = new ColorDto("glasses", chr.getGlasses());
        list[3] = new ColorDto("clothes", chr.getClothes());
        list[4] = new ColorDto("bag", chr.getBag());
        list[5] = new ColorDto("hat", chr.getHat());
        return list;
    }


}
