package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Student;


public class RequestDto {
    private Long r_id;                // 요청 아이디
    private Student student;          // 글 쓴 학생
    private String r_title;           // 제목
    private String r_content;         // 내용


    public RequestDto() {
    }

    public RequestDto(Long r_id, Student student, String r_title, String r_content) {
        this.r_id = r_id;
        this.student = student;
        this.r_title = r_title;
        this.r_content = r_content;
    }

    public void setR_id(Long r_id) {
        this.r_id = r_id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setR_title(String r_title) {
        this.r_title = r_title;
    }

    public void setR_content(String r_content) {
        this.r_content = r_content;
    }

    public Long getR_id() {
        return r_id;
    }

    public Student getStudent() {
        return student;
    }

    public String getR_title() {
        return r_title;
    }

    public String getR_content() {
        return r_content;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "RequestDto{" + "r_id=" + r_id + ", student=" + student + ", r_title='" + r_title + '\'' + ", r_content='" + r_content + '\'' + '}';
    }
}
