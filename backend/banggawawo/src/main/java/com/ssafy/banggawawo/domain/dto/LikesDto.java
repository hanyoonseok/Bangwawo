package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesDto {
    private Long sId;          // 글 쓴 학생
    private Long lId;          // 좋아요 아이디
    private Long rId;          // 요청 아이디
    private String rTitle;     // 요청글 제목
    private Boolean lOpened;   // 수업 개설 여부
    private Boolean lRead;     // 알림 읽음 여부
}
