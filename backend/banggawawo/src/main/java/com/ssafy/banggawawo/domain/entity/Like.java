package com.ssafy.banggawawo.domain.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="likes")
public class Like {
    @Id
    @GeneratedValue
    private Long l_id;          //좋아요 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="s_id")
    private Student student;    //학생 아이디

    private Long rId;           //요청 아이디
    private Boolean lOpened;   // 수업 개설 여부
    private Boolean lRead;     // 알림 읽음 여부

    @Builder
    public Like(Student student,Long rId,boolean lOpened,boolean lRead) {
        this.student = student;
        this.rId = rId;
        this.lOpened = lOpened;
        this.lRead = lRead;
    }
}
