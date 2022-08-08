package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.LikesDto;
import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.service.LikesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikesController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private LikesService likeService;

    // 요청글에 대한 공감
    @PostMapping()
    @ApiOperation(value = "요청글에 대한 공감")
    public ResponseEntity<?> sympathy(@RequestBody LikesDto likeDto) throws Exception {
        System.out.println("들어옴??");
        System.out.println(likeDto.getRId().toString()+"////1번입니다");
        System.out.println(likeDto.getSId().getSId().toString()+"///2번입니다");
        System.out.println(likeDto.getLId().toString()+"///2번입니다");
        Optional<Likes> likes = likeService.readonly(likeDto.getLId());
        // 요청글에대한 공감을 이미 했는지 안했는지 확인작업
        if(!likes.isPresent()){
            System.out.println("이미 작성");
            // 이미 공감버튼을 누른 경우 삭제된다.
            likeService.delete(likeDto.getLId());
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else{
            System.out.println("새로 작성");
            if (likeService.sympathy(likeDto) > 0)
                return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
            else
                return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
        }
    }
}
