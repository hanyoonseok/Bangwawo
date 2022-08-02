package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Student;


public class RequestDto {
    private Long r_id;                // 요청 아이디
    private Student student;          // 글 쓴 학생
    private ClassRoom classRoom;      // 수업 아이디
    private String r_title;           // 제목
    private String r_content;         // 내용
    private boolean r_solved;         // 해결여부
    private int r_count;              // 조회수

    public RequestDto() {
    }

    public RequestDto(Long r_id, Student student, ClassRoom classRoom, String r_title, String r_content, boolean r_solved, int r_count) {
        this.r_id = r_id;
        this.student = student;
        this.classRoom = classRoom;
        this.r_title = r_title;
        this.r_content = r_content;
        this.r_solved = r_solved;
        this.r_count = r_count;
    }

    public Long getR_id() {
        return r_id;
    }

    public void setR_id(Long r_id) {
        this.r_id = r_id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public String getR_title() {
        return r_title;
    }

    public void setR_title(String r_title) {
        this.r_title = r_title;
    }

    public String getR_content() {
        return r_content;
    }

    public void setR_content(String r_content) {
        this.r_content = r_content;
    }

    public boolean isR_solved() {
        return r_solved;
    }

    public void setR_solved(boolean r_solved) {
        this.r_solved = r_solved;
    }

    public int getR_count() {
        return r_count;
    }

    public void setR_count(int r_count) {
        this.r_count = r_count;
    }

    @Override
    public String toString() {
        return "RequestDto{" + "r_id=" + r_id + ", student=" + student + ", classRoom=" + classRoom + ", r_title='" + r_title + '\'' + ", r_content='" + r_content + '\'' + ", r_solved=" + r_solved + ", r_count=" + r_count + '}';
    }
}
