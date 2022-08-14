package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.repository.ClassRepository;
import com.ssafy.banggawawo.repository.LikesRepository;
import com.ssafy.banggawawo.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ClassRepository classRepository;

    // 요청글 작성
    @Transactional
    public Request write(RequestDto requestDto) throws Exception {
        Request request = Request.builder()
                .student(requestDto.getSId())
                .content(requestDto.getContent())
                .title(requestDto.getTitle())
                .createDate(LocalDate.now())
                .solved(false)
                .count(0)
                .likes(0)
                .build();

        return requestRepository.save(request);
    }

    //요청글 목록
    @Transactional(readOnly = true)
    public Map<String, Object> list(int scrollcnt) throws Exception {
        System.out.println(scrollcnt);

        int size  =  requestRepository.findAll().size()/6;
        int remain =requestRepository.findAll().size()%6;
        HashMap<String, Object> result = new HashMap<String, Object>();
        if(remain ==0) {
            Page<Request> requestsList = requestRepository.findAll(PageRequest.of(size - (scrollcnt - 1), 6));
            result.put("requestsList", requestsList);
        }else{
            Page<Request> requestsList = requestRepository.findAll(PageRequest.of(size-scrollcnt+1, 6));
            result.put("requestsList", requestsList);
        }
        return result;
    }

    //검색요청글 목록
    @Transactional(readOnly = true)
    public Map<String, Object> searchlist(String topic) throws Exception {
        List<Request> requestsList = requestRepository.findByContentContaining(topic);
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("requestsList", requestsList);
        return result;
    }

    //요청글 상세보기
    @Transactional // 조회
    public Map<String, Object> read(Long rid) throws Exception {
        HashMap<String, Object> hashmap = new HashMap<String, Object>();

        Optional<Request> request = requestRepository.findById(rid);
        if (request.isPresent()) {
            //요청글 조회수
            request.get().setCount(request.get().getCount()+1);
            System.out.println(request.get().isSolved());
            if(request.get().isSolved()){
                Optional<ClassRoom> classinfo = classRepository.findByRId(rid);
                hashmap.put("classinfo", classinfo.get());
            }

            requestRepository.save(request.get());
        }
        hashmap.put("requsest", request.get()); // 현재 읽는 게시글 내용
        return hashmap;
    }

    //요청글중 미완료 목록
    @Transactional(readOnly = true)
    public Map<String, Object> unfindlist() throws Exception {
        List<Request> requestsList = requestRepository.unfindlist();
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("requestsList", requestsList);
        return result;
    }


    @Transactional(readOnly = true) // 조회 시 데이터 변경 방지
    public Optional<Request> readonly(Long rid) throws Exception {
        Optional<Request> request = requestRepository.findById(rid);
        return request;
    }

    //요청글 수정
    @Transactional
    public void update(Request request) throws Exception {
        requestRepository.save(request);
    }

    //요청글 삭제
    @Transactional
    public int delete(Long rid) throws Exception {
        Optional<Request> request = requestRepository.findById(rid);
        if(request.isPresent()){
            requestRepository.delete(request.get());
            return 1;
        }
        else
            return 0;
    }
}



