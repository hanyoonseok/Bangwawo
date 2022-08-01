package com.ssafy.banggawawo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecritChat {
    @Id
    @GeneratedValue
    private Long sc_id;           //비밀친구 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="s_id")
    private Student student;

    private String sc_content;    //위험 내용
    private Date sc_date;         //날짜
}
