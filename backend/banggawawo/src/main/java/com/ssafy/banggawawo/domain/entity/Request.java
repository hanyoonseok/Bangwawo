package com.ssafy.banggawawo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Request {
    @Id
    @GeneratedValue
    private Long r_id;          // 요청 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="s_id")
    private Student student;          // 글 쓴 학생

    @OneToOne(fetch = FetchType.LAZY)
    private ClassRoom classRoom;          // 수업 아이디

    private String r_title;     // 제목
    private String r_content;   // 내용
    private boolean r_solved;   // 해결여부

    @OneToMany
    private List<Student> like; // 좋아요
    private int r_count;        // 조회수

}
