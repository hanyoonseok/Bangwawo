package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Character;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VolunteerDto {
    private Long vId;          // 봉사자 아이디
    private String token;     // 카카오 토큰
    private String name;      // 이름
    private int birth;        // 출생년도
    private String introduce; // 자기소개
    private Character character;//개인 3d캐릭터에대한 정보
}
