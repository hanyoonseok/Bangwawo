package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    // 요청글 작성
    public int write(RequestDto requestDto) throws Exception {
        return requestRepository.insert(requestDto);
    }
    //요청글 목록


    //요청글 상세보기
    public Map<String, Object> read(int rno, String userid) throws Exception {
        RequestDto requestDto = requestRepository.selectOne(rno);
        if (requestDto != null && !requestDto.getStudent().equals(userid)) {
            //글조회수
        }
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("requsest", requestDto); // 현재 읽는 게시글 내용

        return hashmap;
    }


    //요청글 수정
    public int update(RequestDto requestDto) throws SQLException {
        return requestRepository.update(requestDto);
    }

    //요청글 삭제
    public int delete(int rno) throws Exception {
        return requestRepository.delete(rno);
    }


}



