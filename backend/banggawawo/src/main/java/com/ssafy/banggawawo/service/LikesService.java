package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.LikesDto;
import com.ssafy.banggawawo.repository.LikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikesService {
    @Autowired
    private LikesRepository likeRepository;

    @Transactional
    public int sympathy(LikesDto likesDto) throws Exception {
//        Likes likes = Likes.builder()
//                .student(likesDto.)
//                        .rId(likesDto.getRId())
//                        .lOpened(false)
//                        .lRead(false)
//                        .build();
//
//
////        return likeRepository.save(likes)!=null?1:0;
        return 0;
    }


    @Transactional
    public int unsympathy(LikesDto likeDto) throws Exception {
//        Likes like = Likes.builder()
//                .rId(likeDto.getRId())
//                .lOpened(false)
//                .lRead(false)
//                .build();
//
        return 0;
    }
}
