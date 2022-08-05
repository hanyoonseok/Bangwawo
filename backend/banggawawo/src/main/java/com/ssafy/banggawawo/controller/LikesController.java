package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.LikesDto;
import com.ssafy.banggawawo.service.LikesService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikesController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private LikesService likeService;

    // 요청글에 대한 공감
    @GetMapping()
    @ApiOperation(value = "요청글에 대한 공감")
    public ResponseEntity<?> sympathy(@RequestBody LikesDto likeDto) throws Exception {
        if (likeService.sympathy(likeDto) > 0)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    // 요청글에대한 공감 제거
    @DeleteMapping("/{rid}")
    @ApiOperation(value = "요청글에대한 공감 제거")
    public ResponseEntity<?> unsympathy(@RequestBody LikesDto likeDto) throws Exception {
        if (likeService.unsympathy(likeDto) > 0)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

}
