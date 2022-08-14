package com.ssafy.banggawawo.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor
public class NoticeDto {
    private StudentFrontDto student; // 학생 정보

    private VolunteerFrontDto volunteer; // 봉사자 정보

    private String message;

    public NoticeDto(StudentFrontDto student, VolunteerFrontDto volunteer, String message) {
        this.student = student;
        this.volunteer = volunteer;
        this.message = message;
    }
}
