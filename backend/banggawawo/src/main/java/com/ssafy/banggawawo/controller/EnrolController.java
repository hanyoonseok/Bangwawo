package com.ssafy.banggawawo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.banggawawo.domain.dto.EmotionDto;
import com.ssafy.banggawawo.domain.dto.EnrolDto;
import com.ssafy.banggawawo.domain.dto.StudentDto;
import com.ssafy.banggawawo.domain.dto.StudentFrontDto;
import com.ssafy.banggawawo.domain.entity.Emotion;
import com.ssafy.banggawawo.domain.entity.Enrol;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.service.EnrolService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrol")
public class EnrolController {
    private final EnrolService enrolService;
    @Autowired
    public EnrolController(EnrolService enrolService) {
        this.enrolService = enrolService;
    }

    @ApiOperation(value = "수강신청 등록하기", notes = "필수 : sid, cid")
    @PostMapping()
    public ResponseEntity<EnrolDto> save(@RequestBody EnrolDto enrolDto) throws Exception{
        try{    //이미 수강신청 했는지 확인
            System.out.println("cid = " + enrolDto.getCId().getCId());
            System.out.println("sid = " + enrolDto.getSId().getSId());
            enrolService.findByClassAndStudent(enrolDto.getCId().getCId(), enrolDto.getSId().getSId());
            System.out.println("수강신청 했음");
        }catch(Exception e){    //수강신청 안했으면 저장해줌
            System.out.println("수강신청 안함");
            enrolDto = enrolService.save(enrolDto);
            if(enrolDto != null){
                return new ResponseEntity<>(enrolDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @ApiOperation("수업에 수강신청한 학생 받아오기")
    @GetMapping("/class/{cid}")
    public ResponseEntity<List<StudentFrontDto>> findByClassRoom(@PathVariable("cid") Long cId) throws Exception{
        List<Enrol> enrolList = enrolService.findByClassRoom(cId);
        List<StudentFrontDto> response = new ArrayList<>();
        for(Enrol e : enrolList){
            Student student = e.getStudent();
            response.add(new StudentFrontDto(new StudentDto(student)));
        }
        return new ResponseEntity<>(response ,HttpStatus.OK);
    }
    @ApiOperation("학생이 수강신청한 수업 받아오기")
    @GetMapping("/student/{sid}")
    public ResponseEntity<List<Enrol>> findByStudent(@PathVariable("sid") Long sId) throws Exception{
        return new ResponseEntity<>(enrolService.findByStudent(sId), HttpStatus.OK);
    }


    @ApiOperation("봉사자 피드백 작성")
    @PutMapping("/feedback")
    public ResponseEntity<?> updateFeedBack(@RequestBody Map<String, Object> request) throws Exception{
        Long cid = Long.parseLong(request.get("cid").toString());
        Long sid = Long.parseLong(request.get("sid").toString());
        String feedback = request.get("feedback").toString();

        return new ResponseEntity<>(enrolService.updateFeedback(cid, sid, feedback),HttpStatus.OK);
    }
    @ApiOperation("한 학생의 한 수업에 대한 피드백 가져오기")
    @GetMapping("/{cid}/{sid}")
    public ResponseEntity<?> saveFeedback(@PathVariable("cid") Long cid, @PathVariable("sid") Long sid) throws Exception{
            return new ResponseEntity<>(enrolService.findByClassAndStudent(cid, sid), HttpStatus.OK);
    }

    @ApiOperation("감정 피드백 저장")
    @PutMapping("/emotion")
    public ResponseEntity<?> updateEmotion(@RequestBody Map<String, Object> request) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        EmotionDto emotionDto = mapper.convertValue(request.get("emotion"), new TypeReference<EmotionDto>() {});

        Long cid = Long.parseLong(request.get("cid").toString());
        Long sid = Long.parseLong(request.get("sid").toString());

        System.out.println("cid = " + cid);
        System.out.println("sid = " + sid);
        Emotion emotion = new Emotion(
            (int) Math.round(emotionDto.getAngry()*100),
            (int) Math.round(emotionDto.getDisgusted()*100),
            (int) Math.round(emotionDto.getFearful()*100),
            (int) Math.round(emotionDto.getHappy()*100),
            (int) Math.round(emotionDto.getNeutral()*100),
            (int) Math.round(emotionDto.getSad()*100),
            (int) Math.round(emotionDto.getSurprised()*100)
        );
        return new ResponseEntity<>(enrolService.updateEmotion(cid, sid, emotion),HttpStatus.OK);
    }

}
