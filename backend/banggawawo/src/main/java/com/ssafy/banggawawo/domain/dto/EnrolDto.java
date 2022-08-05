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
}
