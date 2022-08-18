package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.LikesDto;
import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.service.LikesService;
import com.ssafy.banggawawo.service.RequestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/request")
public class RequestController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private RequestService requestservice;
    @Autowired
    private LikesService likesService;

    //요청글 작성 (sid,content,title을 입력받는다.)
    @PostMapping()
    @ApiOperation(value = "요청글 작성")
    public ResponseEntity<?> write(@RequestBody RequestDto requestdto) throws Exception {
        Request request =requestservice.write(requestdto);
        if (request.getRId() > 0){
            // 요청글 작성시 자동을 공감 추가

            LikesDto result= new LikesDto();
            result.setRId(request.getRId());
            result.setSId(requestdto.getSId().getSId());
            result.setRTitle(requestdto.getTitle());
            likesService.sympathy(result);

            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);}
        else
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }

    //요청글 리스트 보기 (스크롤 횟수(scrollcnt)를 입력받는다)
    @GetMapping()
    @ApiOperation(value = "요청글 전체 목록보기(한번스크롤당 6개씩 추가)")
    public ResponseEntity<?> list(@RequestParam(value = "scrollcnt", defaultValue = "1") int scrollcnt) {
        try {
            return new ResponseEntity<Map<String, Object>>(requestservice.list(scrollcnt), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //검색한 요청글 리스트 보기 (검색어 입력받는다)
    @GetMapping("/topic/{topic}")
    @ApiOperation(value = "검색어로 요청글 리스트 보기")
    public ResponseEntity<?> searchlist(@PathVariable("topic") String topic) {
        try {
            return new ResponseEntity<Map<String, Object>>(requestservice.searchlist(topic), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 미해결 요청 리스트
    @GetMapping("/unfind")
    @ApiOperation(value = "미해결요청글 전체 목록보기")
    public ResponseEntity<?> unfindlist() {
        try {
            return new ResponseEntity<Map<String, Object>>(requestservice.unfindlist(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //요청글 상세보기 (rid와 userid 입력받는다)
    @GetMapping("/{rid}/{sid}")
    @ApiOperation(value = "요청글 상세보기")
    public ResponseEntity<?> read(@PathVariable("rid") Long rid,@PathVariable("sid") Long sid) throws Exception {
        try {
            return new ResponseEntity<Map<String, Object>>(requestservice.read(rid,sid), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //작성글 수정 (content와 title을 입력받는다)
    @PutMapping()
    @ApiOperation(value = "요청글 수정")
    public String update(@RequestBody RequestDto requestdto) throws Exception {
        Optional<Request> orequest = requestservice.readonly(requestdto.getRId());
        if (orequest.isPresent()) {
            orequest.get().setTitle(requestdto.getTitle());
            orequest.get().setContent(requestdto.getContent());
            requestservice.update(orequest.get());
            return "요청글 수정이 완료되었습니다.";
        } else
            return "요청글 수정에 실패했습니다.";
    }

    //작성글 삭제 (rid 글번호만 입력 받는다.)
    @DeleteMapping("/{rid}")
    @ApiOperation(value = "요청글 삭제")
    public ResponseEntity<String> delete(@PathVariable("rid") Long rid) throws Exception {
        if (requestservice.delete(rid) > 0)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        else
            return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
    }
}
