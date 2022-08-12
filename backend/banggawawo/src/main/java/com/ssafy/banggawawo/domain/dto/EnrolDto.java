package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Emotion;
import com.ssafy.banggawawo.domain.entity.Student;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class EnrolDto {
    private Long eId;                  //신청 아이디
    private Student sId;           //학생
    private ClassRoom cId;       //수업

    private String feedback;            //봉사자 피드백
    private Emotion emotion;             //감정 피드백
    private String recording;           //녹화영상

    @Builder
    public EnrolDto(Long eId,
                    Student sId, ClassRoom cId,
                    String feedback, Emotion emotion, String recording) {
        this.eId = eId;
        this.sId = sId;
        this.cId = cId;
        this.feedback = feedback;
        this.emotion = emotion;
        this.recording = recording;
    }

}
