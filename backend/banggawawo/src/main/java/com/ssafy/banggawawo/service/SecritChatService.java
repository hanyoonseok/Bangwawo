package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.domain.dto.SecritChatDto;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.domain.entity.SecritChat;
import com.ssafy.banggawawo.repository.SecritChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SecritChatService {
    @Autowired
    private SecritChatRepository secritChatRepository;

    @Transactional
    public int warning(SecritChatDto secritChatDto) throws Exception {
        SecritChat secritChat = SecritChat.builder()
                .student(secritChatDto.getStudent())
                .scContent(secritChatDto.getScContent())
                .scDate(secritChatDto.getScDate())
                .parentsCheck(false)
                .build();
        return secritChatRepository.save(secritChat) != null ? 1 : 0;
    }

    @Transactional(readOnly = true) // 조회 시 데이터 변경 방지
    public Optional<SecritChat> readonly(Long scid) throws Exception {
        Optional<SecritChat> request = secritChatRepository.findById(scid);
        return request;
    }

    @Transactional
    public void update(SecritChat request) throws Exception {
        secritChatRepository.save(request);
    }
}
