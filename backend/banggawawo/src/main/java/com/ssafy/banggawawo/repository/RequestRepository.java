package com.ssafy.banggawawo.repository;


import com.ssafy.banggawawo.domain.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
    //요청글 작성
    //RequestDto save(Request request);
    //조회수 증가
//    Request save(Long rno);
    //요청글 수정
//    Request save(Request request,Long rno);
    //전체 글수
//    long count();
    //요청글 삭제
//    void delete(Long rno);
    //요청글 상세 보기
//    Optional<Request> findById(Long rno);
//    Optional<Request> findById(Long rno);
    //요청글 전체 보기
//    List<Request> findAll(int scroll, int cnt);

}
