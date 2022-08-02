package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.domain.dto.UpdateRequestDto;
import com.ssafy.banggawawo.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/request")
public class RequestController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private RequestService requestservice;

    //요청글 작성
    @PostMapping()
    public ResponseEntity<?> write(@RequestBody RequestDto requestdto) throws Exception {
        if (requestdto.r_title.length > 0 && requestdto.r_content.length > 0)
            return new ResponseEntity<RequestDto>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<RequestDto>(FAIL, HttpStatus.NO_CONTENT);
    }

    //요청글 상세보기보기
    @GetMapping("/{rno}/{userid}")
    public ResponseEntity<?> read(@PathVariable("rno") int rno,@PathVariable("userid") String userid) throws Exception {
        try {
            return new ResponseEntity<Map<String, Object>>(requestservice.read(rno, userid), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //작성글 삭제
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> delete(@PathVariable("rno") int rno) throws Exception {
        if (requestservice.delete(rno) > 0)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    //작성글 수정
    @PutMapping("/{nno}")
    public String update(@RequestBody UpdateRequestDto updaterequestdto) throws SQLException {
        if (requestservice.update(updaterequestdto) > 0)
            return "요청글 수정이 완료되었습니다.";
        else
            return "요청글 수정에 실패했습니다.";
    }



}
