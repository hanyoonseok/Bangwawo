package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private Student student;          // 글 쓴 학생
    private ClassRoom classRoom;      // 수업 아이디
    private String rTitle;           // 제목
    private String rContent;         // 내용
    private boolean rSolved;         // 해결여부
    private int rCount;              // 조회수
}
