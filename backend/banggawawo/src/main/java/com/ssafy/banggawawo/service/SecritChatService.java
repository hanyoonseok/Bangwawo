package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.domain.dto.SecritChatDto;
import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.domain.entity.SecritChat;
import com.ssafy.banggawawo.repository.SecritChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SecritChatService {
    @Autowired
    private SecritChatRepository secritChatRepository;

    @Transactional
    public int warning(SecritChatDto secritChatDto) throws Exception {
        SecritChat secritChat = SecritChat.builder()
                .sId(secritChatDto.getSId())
                .sName(secritChatDto.getSName())
                .sccontent(secritChatDto.getSccontent())
                .parentsCheck(false)
                .build();
        return secritChatRepository.save(secritChat) != null ? 1 : 0;
    }

    @Transactional(readOnly = true) // 조회 시 데이터 변경 방지
    public Map<String, Object> findByIdAndparentsCheck(Long sid) throws Exception {
        List<SecritChat> result = secritChatRepository.findByIdAndparentsCheck(sid);

        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("requsest", result);
        return hashmap;
    }

    @Transactional(readOnly = true) // 조회 시 데이터 변경 방지
    public Optional<SecritChat> findById(Long scid) throws Exception {
        System.out.println("글아이디로 글찾아오기?");
        return secritChatRepository.findById(scid);
    }
    @Transactional
    public void updateboolean(SecritChat secritChat) throws Exception {
        secritChatRepository.save(secritChat);
    }
}
