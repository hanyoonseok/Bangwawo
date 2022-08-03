package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    // 요청글 작성
    @Transactional
    public int write(RequestDto requestDto) throws Exception {
        Request request = Request.builder()
                .student(requestDto.getStudent())
                .classRoom(requestDto.getClassRoom())
                .rContent(requestDto.getRContent())
                .rTitle(requestDto.getRTitle())
                .rSolved(false)
                .rCount(requestDto.getRCount())
                .build();

        return requestRepository.save(request) != null ? 1 : 0;
    }

    //요청글 목록
    @Transactional(readOnly = true)
    public Map<String, Object> list(int scrollcnt) throws Exception {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("scrollcnt", scrollcnt);

        int startScroll = (scrollcnt - 1) / 10 * 10 + 1; // 11~20 모두 11페이지가 첫번째임
        result.put("startScroll", startScroll);

        long totalCount = requestRepository.count();
        int totalPage = (int)totalCount / 10; // 한 페이지당 게시글 10개
        if (totalCount % 10 > 0) { // 게시글이 56개이면 56/10 = 5페이진데 한페이지 더 필요함
            totalPage++;
        }
        result.put("totalPage", totalPage);

        int endScroll = startScroll + 9; // 현재 페이지18인 경우 하단 페이지는 11~20
        if (endScroll > totalPage) { // 만약 총 페이지 수는 37인데 위에서 더한 endPage는 40임 그래서 총 페이지보다 endPage가 더 크면 endPage를 총
            endScroll = totalPage;
        }
        result.put("endScroll", endScroll);

        List<Request> requestDtoList = requestRepository.findAll();
        List<Request> requestDtoListcut = new ArrayList<Request>();
        for (int i =(scrollcnt - 1) * 10;i<=(scrollcnt - 1) * 10 +10 ;i++){
            requestDtoListcut.add(requestDtoList.get(i));
        }

        result.put("requestDtoListcut", requestDtoListcut);

        return result;
    }

    //요청글 상세보기
    @Transactional(readOnly = true) // 조회 시 데이터 변경 방지
    public Map<String, Object> read(Long rno, String userid) throws Exception {
        Optional<Request> request = requestRepository.findById(rno);
        if (request != null && !request.equals(userid)) {
            //요청글 조회수
            requestRepository.save(request.get());
            request = requestRepository.findById(rno);
        }
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("requsest", request); // 현재 읽는 게시글 내용

        return hashmap;
    }

    //요청글 수정
    @Transactional
    public int update(RequestDto requestDto) throws Exception {
        Request request = Request.builder()
                .student(requestDto.getStudent())
                .classRoom(requestDto.getClassRoom())
                .rContent(requestDto.getRContent())
                .rTitle(requestDto.getRTitle())
                .rSolved(requestDto.isRSolved())
                .rCount(requestDto.getRCount())
                .build();
        return requestRepository.save(request)!=null ? 1:0;
    }

    //요청글 삭제
    @Transactional
    public int delete(Long rno) throws Exception {
        Optional<Request> request = requestRepository.findById(rno);
        if(request!=null){
            requestRepository.delete(request.get());
            return 1;
        }
        else
            return 0;
    }


}



