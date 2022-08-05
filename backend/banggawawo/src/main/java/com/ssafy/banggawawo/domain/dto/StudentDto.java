package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Character;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudentDto {
    @ApiModelProperty(example = "12345")
    private Long sId;           //학생 아이디
    private String token;      //카카오 회원번호
    private int ageRange;         //연령대
    private String nickname;   //별명
    private String pemail;     //부모이메일
    private String ppw;        //부모 비밀번호
    private Character character; //개인 3d 캐릭터에 대한 정보

}
