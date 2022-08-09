package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.SecritChatDto;
import com.ssafy.banggawawo.domain.entity.SecritChat;
import com.ssafy.banggawawo.service.SecritChatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class SecritChatController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @Autowired
    private SecritChatService secritChatService;

    @PostMapping()
    @ApiOperation(value = "위험 내용이 발생할경우 위험발언 내용을 저장해준다")
    public ResponseEntity<?> warning(@RequestBody SecritChatDto secritChatDto) throws Exception {
        if (secritChatService.warning(secritChatDto) > 0)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{sid}")
    @ApiOperation(value = "부모 로그인시 위험 내용이 있으면 알람기능")
    public ResponseEntity<?> list(@PathVariable("sid") Long sid) throws Exception{
        try {
            return new ResponseEntity<Map<String, Object>>(secritChatService.findByIdAndparentsCheck(sid), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/read")
    @ApiOperation(value = "알람에대한 읽기 완료")
    public ResponseEntity<?> alarmread(@RequestBody SecritChatDto secritChatDto) throws Exception{
        System.out.println(secritChatDto.getScId());
        Optional<SecritChat> orequest = secritChatService.findById(secritChatDto.getScId());
        orequest.get().setParentsCheck(true);
        secritChatService.updateboolean(orequest.get());
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
}
