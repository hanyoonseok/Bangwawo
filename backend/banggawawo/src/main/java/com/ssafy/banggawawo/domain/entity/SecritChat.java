package com.ssafy.banggawawo.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecritChat {
    @Id
    @GeneratedValue
    private Long scId;            //비밀친구 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="s_id")
    private Student student;

    private String scContent;     //위험 내용
    private Date scDate;          //날짜
    private Boolean parentsCheck; // 부모님 알람 확인

    @Builder
    public SecritChat(Student student, String scContent, Date scDate,boolean parentsCheck) {
        this.student = student;
        this.scContent = scContent;
        this.scDate = scDate;
        this.parentsCheck = parentsCheck;
    }
}
