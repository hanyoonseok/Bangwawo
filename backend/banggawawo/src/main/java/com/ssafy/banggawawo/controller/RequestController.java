package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.service.RequestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/request")
public class RequestController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private RequestService requestservice;

    //요청글 작성
    //요청글 리스트 보기

    @PostMapping()
    @ApiOperation(value = "요청글 작성")
    public ResponseEntity<?> write(@RequestBody RequestDto requestdto) throws Exception {
        if (requestservice.write(requestdto) > 0) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }
    // 요청글 목록
    @GetMapping("/{scrollcnt}")
    @ApiOperation(value = "요청글 전체 목록보기(한번스크롤당 10개씩 추가)")
    public ResponseEntity<?> list(@RequestParam(value = "scrollcnt", defaultValue = "1") int page) {
        try {
            return new ResponseEntity<Map<String, Object>>(requestservice.list(page), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //요청글 상세보기
    @GetMapping("/{rno}/{userid}")
    @ApiOperation(value = "요청글 상세보기")
    public ResponseEntity<?> read(@PathVariable("rno") Long rno, @PathVariable("userid") String userid) throws Exception {
        try {
            return new ResponseEntity<Map<String, Object>>(requestservice.read(rno, userid), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //작성글 수정
    @PutMapping()
    @ApiOperation(value = "요청글 수정")
    public String update(@RequestBody RequestDto requestdto) throws Exception {
        if (requestservice.update(requestdto) > 0)
            return "요청글 수정이 완료되었습니다.";
        else
            return "요청글 수정에 실패했습니다.";
    }

    //작성글 삭제
    @DeleteMapping("/{rno}")
    @ApiOperation(value = "요청글 삭제")
    public ResponseEntity<String> delete(@PathVariable("rno") Long rno) throws Exception {
        if (requestservice.delete(rno) > 0)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }


}
