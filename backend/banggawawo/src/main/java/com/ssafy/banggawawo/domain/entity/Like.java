package com.ssafy.banggawawo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {
    @Id
    @GeneratedValue
    private Long l_id;          //좋아요 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="s_id")
    private Student student;    //학생 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="r_id")
    private Request request;    //요청 아이디

    private boolean l_opened;   // 수업 개설 여부
    private boolean l_read;     // 알림 읽음 여부
}
