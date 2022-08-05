package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.LikeDto;
import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.service.LikeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private LikeService likeService;

    // 요청글에 대한 공감
    @GetMapping("/{rid}")
    @ApiOperation(value = "요청글에 대한 공감")
    public ResponseEntity<?> sympathy(@PathVariable("rid") Long rid) throws Exception {
        if (likeService.sympathy(rid) > 0)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    // 요청글에대한 공감 제거
    @DeleteMapping("/{rid}")
    @ApiOperation(value = "요청글에대한 공감 제거")
    public ResponseEntity<?> unsympathy(@PathVariable("rid") Long rid) throws Exception {
        if (likeService.unsympathy(rid) > 0)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

}
