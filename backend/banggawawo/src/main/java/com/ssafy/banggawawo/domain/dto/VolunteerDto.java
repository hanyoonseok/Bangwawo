package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Character;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VolunteerDto {
    private Long vId;          // 봉사자 아이디
    private String kakaoId;     // 카카오 회원번호
    private String nickname;      // 별명
    private int ageRange;        // 연령대
    private String introduce; // 자기소개
    private Character character;//개인 3d 캐릭터에 대한 정보
}
