package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Student;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private Long rId;               // 요청글 아이디
    private Student sId;        // 글 쓴 학생
    private String Title;           // 제목
    private String Content;         // 내용
    private Boolean Solved;         // 해결여부
    private int Count;              // 조회수
    private LocalDate createDate;   // 생성 날짜
}
