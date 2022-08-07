package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.EnrolDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Enrol;
import com.ssafy.banggawawo.service.EnrolService;
import io.swagger.annotations.ApiOperation;
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
    public ResponseEntity<Enrol> save(@RequestBody EnrolDto enrolDto){
        return new ResponseEntity<>(enrolService.save(enrolDto), HttpStatus.OK);
    }

    @ApiOperation("수업에 수강신청한 학생 받아오기")
    @GetMapping("/class/{id}")
    public ResponseEntity<List<Enrol>> findByClassRoom(@PathVariable("id") Long cId){
        EnrolDto enrolDto = new EnrolDto();
        enrolDto.setClassRoom(ClassRoom.builder().cId(cId).build());
        return new ResponseEntity<>(enrolService.findByClassRoom(enrolDto) ,HttpStatus.OK);
    }

    @ApiOperation("학생이 수강신청한 수업 받아오기")
    @GetMapping("/student/{id}")
    public ResponseEntity<List<Enrol>> findByStudent(@PathVariable("id") Long sId){
        EnrolDto enrolDto = new EnrolDto();
        //enrolDto.setStudent(Student.builder().);
        //return new ResponseEntity<>(enrolService.findByStudent(sId), HttpStatus.OK);
        return null;
    }

}
