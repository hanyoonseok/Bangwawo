package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Character;
import com.ssafy.banggawawo.domain.entity.Student;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class StudentDto {
    @ApiModelProperty(example = "12345")
    private Long sId;           //학생 아이디
    private String kakaoId;      //카카오 회원번호
    private int ageRange;         //연령대
    private String nickname;   //별명
    private String pemail;     //부모이메일
    private String ppw;        //부모 비밀번호
    private Character character; //개인 3d 캐릭터에 대한 정보

    StudentDto () {}

    public StudentDto (Student student){
        this.sId = student.getSId();
        this.kakaoId = student.getKakaoId();
        this.ageRange = student.getAgeRange();
        this.nickname = student.getNickname();
        this.pemail = student.getPemail();
        this.ppw = student.getPpw();
        this.character = student.getCharacter();
    }

}
