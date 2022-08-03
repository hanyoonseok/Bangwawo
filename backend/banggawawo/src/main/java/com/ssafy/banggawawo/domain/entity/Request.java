package com.ssafy.banggawawo.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Request {
    @Id
    @GeneratedValue
    private Long rId;          // 요청 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sId")
    private Student student;          // 글 쓴 학생

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "request")
    private ClassRoom classRoom;          // 수업 아이디

    private String rTitle;     // 제목
    private String rContent;   // 내용
    private Boolean rSolved;   // 해결여부

    @OneToMany
    private List<Student> likes = new ArrayList<>(); // 좋아요
    private int rCount;        // 조회수

    @Builder
    public Request(Student student, ClassRoom classRoom, String rTitle, String rContent, Boolean rSolved, int rCount) {
        this.student = student;
        this.classRoom = classRoom;
        this.rTitle = rTitle;
        this.rContent = rContent;
        this.rSolved = rSolved;
        this.rCount = rCount;
    }

}
