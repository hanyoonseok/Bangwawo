package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Character;
import com.ssafy.banggawawo.domain.entity.Student;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
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

    public StudentDto (Student student){
        this.sId = student.getSId();
        this.kakaoId = student.getKakaoId();
        this.ageRange = student.getAgeRange();
        this.nickname = student.getNickname();
        this.pemail = student.getPemail();
        this.ppw = student.getPpw();
        this.character = student.getCharacter();
    }

    public StudentDto (StudentFrontDto student){
        this.sId = student.getSId();
        this.kakaoId = student.getKakaoId();
        this.ageRange = student.getAgeRange();
        this.nickname = student.getNickname();
        this.pemail = student.getPemail();
        this.ppw = student.getPpw();
        this.character = student.toClass(student.getCharacter());
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
