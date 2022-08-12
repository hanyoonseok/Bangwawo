package com.ssafy.banggawawo.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enrol {
    @Id
    @GeneratedValue
    private Long eId;            //신청 아이디

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sId")
    private Student student;      //학생

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cId")
    private ClassRoom classes;            //수업

    private String feedback;    //봉사자 피드백
    @Embedded
    private Emotion emotion;     //감정 피드백
    private String recording;   //녹화영상

    @Builder
    public Enrol(Long eId,
                 Student student,
                 ClassRoom classes,
                 String feedback, Emotion emotion,
                 String recording) {
        this.eId = eId;
        this.student = student;
        this.classes = classes;
        this.feedback = feedback;
        this.emotion = emotion;
        this.recording = recording;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Enrol enrol = (Enrol) o;
        return eId.equals(enrol.eId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eId);
    }
}
