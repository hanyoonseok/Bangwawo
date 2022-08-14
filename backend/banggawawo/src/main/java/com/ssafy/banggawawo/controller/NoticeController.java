package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class NoticeController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/vreceive") // 메시지 받는 곳
    @SendTo("/send/talkable")    // 반환할 곳
    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
    public void SendtoVolunteer(NoticeDto noticeDto) {
        System.out.println("NoticeDto = " + noticeDto.getStudent().toString());
        // 학생이 대화 요청한 경우 : student, opened(false), message(수업요청)
        System.out.println("학생의 요청");   
        simpMessagingTemplate.convertAndSend("/send/talkable", noticeDto);
    }

    @MessageMapping("/sreceive") // 메시지 받는 곳
    @SendTo("/send/wait")    // 반환할 곳
    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
    public void SendtoStudent(NoticeDto noticeDto) {
        System.out.println("NoticeDto = " + noticeDto.getStudent().toString());
        // 봉사자가 대화 수락한 경우 -> 대기중이던 학생에게 봉사자 정보 전송
        System.out.println("봉사자의 요청");
        simpMessagingTemplate.convertAndSend("/send/wait", noticeDto);
    }

}


