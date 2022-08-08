package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.domain.dto.SecritChatDto;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.domain.entity.SecritChat;
import com.ssafy.banggawawo.service.SecritChatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class SecritChatController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @Autowired
    private SecritChatService secritChatService;

    @PostMapping()
    @ApiOperation(value = "요청글 작성")
    public ResponseEntity<?> warning(@RequestBody SecritChatDto secritChatDto) throws Exception {
        if (secritChatService.warning(secritChatDto) > 0)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @PutMapping()
    @ApiOperation(value = "부모님 알림 확인후 업데이트")
    public String update(@RequestBody SecritChatDto secritChatDto) throws Exception {
        Optional<SecritChat> secritChat = secritChatService.readonly(secritChatDto.getScId());
        if (secritChat.isPresent()) {
            secritChat.get().setParentsCheck(true);
            secritChatService.update(secritChat.get());
            return "부모님 위험신호 확인 완료.";
        } else
            return "실패.";
    }
}
