package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.domain.entity.Like;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Transactional
    public int sympathy(Long rid) throws Exception {
        Like like = Like.builder()
                        .rId(rid)
                        .lOpened(false)
                        .build();

        return 0;
    }

    @Transactional
    public int unsympathy(Long rid) throws Exception {
        Like like = Like.builder()
                .rId(rid)
                .lOpened(false)
                .build();

        return 0;
    }
}
