package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Student;
import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecritChatDto {
    private Long scId;            // 위험 내용 아이디
    private Long sId;             // 비밀 친구 사용학생
    private String sccontent;     // 위험 내용
    private Boolean parentsCheck; // 부모님 알림 확인 여부
}
