package com.ssafy.banggawawo.domain.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {
    @Id
    @GeneratedValue
    private Long lId;          //좋아요 아이디

    private Long sId;    //학생 아이디
    private Long rId;           //요청글 아이디
    private Boolean lOpened;   // 수업 개설 여부
    private Boolean lRead;     // 알림 읽음 여부

    @Builder
    public Likes(Long sId, Long rId, boolean lOpened, boolean lRead) {
        this.sId = sId;
        this.rId = rId;
        this.lOpened = lOpened;
        this.lRead = lRead;
    }
}
