package com.ssafy.banggawawo.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class NoticeDto {
    private StudentFrontDto student; // 학생 정보

    private VolunteerFrontDto volunteer; // 봉사자 정보

    public NoticeDto(StudentFrontDto student, VolunteerFrontDto volunteer) {
        this.student = student;
        this.volunteer = volunteer;
    }
}
