package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Character;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudentDto {

    private Long s_id;           //학생 아이디
    private String token;      //카카오 토큰
    private String s_name;       // 이름
    private int s_birth;         //출생년도
    private String s_nickname;   //벌명
    private String s_pemail;     //부모이메일
    private String s_ppw;        //부모 비밀번호
    private Character character; //개인 3d 캐릭터에 대한 정보

}
