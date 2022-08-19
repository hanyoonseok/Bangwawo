package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.LikesDto;
import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.service.LikesService;
import com.ssafy.banggawawo.service.RequestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikesController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private LikesService likeService;

    @Autowired
    private RequestService requestservice;

    // 요청글에 대한 공감(rid와 sid입력받기)
    @PostMapping()
    @ApiOperation(value = "요청글에 대한 공감")
    public ResponseEntity<?> sympathy(@RequestBody LikesDto likeDto) throws Exception {
        List<Likes> result = likeService.readonly(likeDto.getRId(), likeDto.getSId());

        if (result.size() == 0) {
            //이전에 공감을 누른적이 없는경우  like db에 추가해준다
            Optional<Request> rrequest = requestservice.readonly(likeDto.getRId());
            likeDto.setRTitle(rrequest.get().getTitle());

            likeService.sympathy(likeDto);
            //공감이 추가되면 request의 공감수 증가
            Optional<Request> orequest = requestservice.readonly(likeDto.getRId());
            orequest.get().setLikes(orequest.get().getLikes()+1);
            requestservice.update(orequest.get());
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        } else {
            //이전에 공감을 누른적이 있는경우 db에서 삭제해준다.
            likeService.delete(result.get(0));
            //공감이 삭제되면 request의 공감수 감소
            Optional<Request> orequest = requestservice.readonly(likeDto.getRId());
            orequest.get().setLikes(orequest.get().getLikes()-1);
            requestservice.update(orequest.get());
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }
    }

    //학생별 요청글에대한 수업 개설시 알람기능 활성화(sid 입력받기)
    @GetMapping("/{sid}")
    @ApiOperation(value = "학생별 요청글에대해 수업 개설시 알람기능")
    public ResponseEntity<?> list(@PathVariable("sid") Long sid) throws Exception{
        try {
            return new ResponseEntity<Map<String, Object>>(likeService.findById(sid), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //학생에게온 알람을 확일했을경우 lread(false->true) 변경 (rid,sid 입력 받기)
    @PostMapping("/read")
    @ApiOperation(value = "학생별 알람에대한 읽기 완료")
    public ResponseEntity<?> alarmread(@RequestBody LikesDto likeDto) throws Exception{
        Likes result = likeService.alarmread(likeDto.getRId(), likeDto.getSId());
        result.setLRead(true);
        likeService.updateboolean(result);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
}
