package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.NoticeDto;
import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
public class NoticeController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final LikesService likeService;

    @MessageMapping("/vreceive")
    public void SendtoVolunteer(NoticeDto noticeDto) {
        System.out.println("NoticeDto = " + noticeDto.getStudent().toString());
        // 학생이 대화 요청한 경우, 대화가능한 봉사자에게 학생 정보 전송
        System.out.println("학생의 요청");
        simpMessagingTemplate.convertAndSend("/send/talkable", noticeDto);
    }

    @MessageMapping("/sreceive")
    public void SendtoStudent(NoticeDto noticeDto) {
        System.out.println("NoticeDto = " + noticeDto.getStudent().toString());
        // 봉사자가 대화 수락한 경우, 대기중이던 학생에게 봉사자 정보 전송
        System.out.println("봉사자의 요청");
        simpMessagingTemplate.convertAndSend("/send/wait/" + noticeDto.getStudent().getSId(), noticeDto);
    }

    @MessageMapping("/opend")
    public void SendtoStudent(Long rid) throws Exception {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setMessage("success");
        System.out.println("rid = " + rid);
        List<Likes> likesList = likeService.likesList(rid);
        for(Likes l : likesList){
            Long sid = l.getSId();
            try{
                simpMessagingTemplate.convertAndSend("/send/wait/" + sid, noticeDto);
            }catch (Exception e){
                System.out.println("sid=" + sid + "는 로그인이 되지 않은 사용자입니다.");
            }
        }
    }

}


