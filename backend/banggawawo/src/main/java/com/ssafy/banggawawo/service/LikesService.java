package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.LikesDto;
import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {
    @Autowired
    private final LikesRepository likeRepository;

    @Transactional
    public int sympathy(LikesDto likesDto) throws Exception {
        Likes likes = Likes.builder()
                .sId(likesDto.getSId())
                .rId(likesDto.getRId())
                .lOpened(false)
                .lRead(false)
                .build();
        return likeRepository.save(likes) != null ? 1 : 0;
    }

    @Transactional
    public void updateboolean(Likes likes) throws Exception {
        likeRepository.save(likes);
    }

    @Transactional(readOnly = true) // 조회 시 데이터 변경 방지
    public List<Likes> readonly(Long rid, Long sid) throws Exception {
        List<Likes> result = likeRepository.findByRIdAndStudentSId(rid, sid);
        return result;
    }

    @Transactional(readOnly = true) // 조회 시 데이터 변경 방지
    public List<Likes> likesList(Long rid) throws Exception {
        List<Likes> result = likeRepository.findByRId(rid);
        return result;
    }

    @Transactional
    public void delete(Likes likes) throws Exception {
        likeRepository.delete(likes);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findById(Long sid) throws Exception {
        List<Likes> request = likeRepository.findBySIdAndLOpenedAAndLRead(sid);

        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("requsest", request); // 현재 읽는 게시글 내용
        return hashmap;
    }

    @Transactional // 조회 시 데이터 변경 방지
    public Likes alarmread(Long rid, Long sid) throws Exception {
        List<Likes> result = likeRepository.findByRIdAndStudentSId(rid, sid);
        return result.get(0);
    }
}
