package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.EnrolDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Enrol;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.service.EnrolService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrol")
public class EnrolController {
    private final EnrolService enrolService;
    @Autowired
    public EnrolController(EnrolService enrolService) {
        this.enrolService = enrolService;
    }

    @ApiOperation("수강신청 등록하기")
    @PostMapping()
    public ResponseEntity<EnrolDto> save(@ApiParam("필수 : sid, cid") @RequestBody EnrolDto enrolDto) throws Exception{
        try{    //이미 수강신청 했는지 확인 
            enrolService.findByClassAndStudent(enrolDto.getCId().getCId(), enrolDto.getSId().getSId());
        }catch(Exception e){    //수강신청 안했으면 저장해줌
            enrolDto = enrolService.save(enrolDto);
            if(enrolDto != null){
                return new ResponseEntity<>(enrolDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @ApiOperation("수업에 수강신청한 학생 받아오기")
    @GetMapping("/class/{cid}")
    public ResponseEntity<List<Enrol>> findByClassRoom(@PathVariable("cid") Long cId) throws Exception{
        return new ResponseEntity<>(enrolService.findByClassRoom(cId) ,HttpStatus.OK);
    }
    @ApiOperation("학생이 수강신청한 수업 받아오기")
    @GetMapping("/student/{sid}")
    public ResponseEntity<List<Enrol>> findByStudent(@PathVariable("sid") Long sId) throws Exception{
        return new ResponseEntity<>(enrolService.findByStudent(sId), HttpStatus.OK);
    }


    @ApiOperation("피드백 작성")
    @PutMapping("/feedback")
    public ResponseEntity<?> update(@RequestBody EnrolDto enrolDto) throws Exception{
        return new ResponseEntity<>(enrolService.update(enrolDto),HttpStatus.OK);
    }
    @ApiOperation("한 학생의 한 수업에 대한 피드백 가져오기")
    @GetMapping("/{cid}/{sid}")
    public ResponseEntity<?> saveFeedback(@PathVariable("cid") Long cid, @PathVariable("sid") Long sid) throws Exception{
            return new ResponseEntity<>(enrolService.findByClassAndStudent(cid, sid), HttpStatus.OK);
    }
}
