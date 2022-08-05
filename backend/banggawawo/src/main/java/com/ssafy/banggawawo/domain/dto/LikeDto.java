package com.ssafy.banggawawo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto {
    private Long l_id;         // 좋아요 아이디
    private Long rId;          // 요청 아이디
    private Boolean lOpened;   // 수업 개설 여부
    private Boolean lRead;     // 알림 읽음 여부
}
