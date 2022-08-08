package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Student;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrolDto {
    private Long eId;                  //신청 아이디
    private Student student;           //학생
    private ClassRoom classRoom;       //수업

    private String feedback;            //봉사자 피드백
    private String emotion;             //감정 피드백
    private String recording;           //녹화영상
}
