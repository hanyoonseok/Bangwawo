package com.ssafy.banggawawo.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sId")
    private Student student;          // 글 쓴 학생

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "request")
    private ClassRoom classRoom;          // 수업 아이디

    private String title;     // 제목
    private String content;   // 내용
    private boolean solved;   // 해결여부
    @CreatedDate
    private LocalDate createDate;  // 글작성일
    private int count;        // 조회수
    private int likes;
    @Builder
    public Request(Student student, ClassRoom classRoom, String title, String content, Boolean solved, int count,int likes,LocalDate createDate) {
        this.student = student;
        this.classRoom = classRoom;
        this.title = title;
        this.content = content;
        this.solved = solved;
        this.count = count;
        this.likes = likes;
        this.createDate=createDate;
    }

}
