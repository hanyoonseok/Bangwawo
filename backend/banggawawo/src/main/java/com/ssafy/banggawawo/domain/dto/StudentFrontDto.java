package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Character;
import com.ssafy.banggawawo.domain.entity.Student;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudentFrontDto {
    @ApiModelProperty(example = "12345")
    private Long sId;           //학생 아이디
    private String kakaoId;      //카카오 회원번호
    private int ageRange;         //연령대
    private String nickname;   //별명
    private String pemail;     //부모이메일
    private String ppw;        //부모 비밀번호
    private ColorDto[] character; //개인 3d 캐릭터에 대한 정보

    public StudentFrontDto() {}
    public StudentFrontDto(StudentDto student){
        this.sId = student.getSId();
        this.kakaoId = student.getKakaoId();
        this.ageRange = student.getAgeRange();
        this.nickname = student.getNickname();
        this.pemail = student.getPemail();
        this.ppw = student.getPpw();
        this.character = student.toArray(student.getCharacter());
    }

    public Character toClass(ColorDto[] arr){
        return new Character(arr[0].getColor(), arr[1].getColor(), arr[2].getColor(),
                arr[3].getColor(), arr[4].getColor(), arr[5].getColor());
    }

}
