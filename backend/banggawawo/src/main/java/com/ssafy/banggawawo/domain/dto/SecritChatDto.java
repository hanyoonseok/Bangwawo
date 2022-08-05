package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Student;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecritChatDto {
    private Student student;      // 비밀 친구 사용학생
    private Long scId;            // 비밀 친구 아이디
    private String scContent;     // 위험 내용
    private Date scDate;          // 날짜
    private Boolean parentsCheck; // 부모님 알림 확인 여부
}
