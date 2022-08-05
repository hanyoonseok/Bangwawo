package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.LikesDto;
import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikesService {
    @Autowired
    private LikesRepository likeRepository;

    @Transactional
    public int sympathy(LikesDto likesDto) throws Exception {
        Likes likes = Likes.builder()
                .student(likesDto.getStudent())
                .rId(likesDto.getRId())
                .lOpened(false)
                .lRead(false)
                .build();
        return likeRepository.save(likes) != null ? 1 : 0;
    }

    @Transactional(readOnly = true) // 조회 시 데이터 변경 방지
    public Optional<Likes> readonly(Long lid) throws Exception {
        Optional<Likes> request = likeRepository.findById(lid);
        return request;
    }


    @Transactional
    public void delete(Long lid) throws Exception {
        Optional<Likes> result = likeRepository.findById(lid);
        likeRepository.delete(result.get());
    }
}
