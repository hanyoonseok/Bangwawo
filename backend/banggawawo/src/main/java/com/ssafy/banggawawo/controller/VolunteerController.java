package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.VolunteerDto;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import com.ssafy.banggawawo.service.VolunteerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(value = "봉사자 관련 정보 처리")
@RequiredArgsConstructor
@RequestMapping(value = "/volunteer", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class VolunteerController {
    private final VolunteerService volunteerService;

    @ApiOperation(value="봉사자 정보 조회", notes="봉사자 id(vId)를 입력받아 봉사자 정보 제공")
    @GetMapping("/{id}")
    public Map<String, Object> findByVolunteerId(
            @ApiParam(value="봉사자 id", required=true, example="1")
            @PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();
        Optional<Volunteer> oVolunteer = volunteerService.findById(id);
        if(oVolunteer.isPresent()){
            response.put("result", "SUCCESS");
            response.put("type", "VOLUNTEER");
            response.put("user", new VolunteerDto(oVolunteer.get()));
        }else{
            response.put("result", "FAIL");
            response.put("reason", "존재하지 않는 토큰");
        }
        return response;
    }

    @ApiOperation(value="봉사자 회원가입", notes="봉사자 정보를 입력받아 회원가입 후 JWT토큰 발급")
    @PostMapping("/")
    public Map<String, Object> saveVolunteer(@RequestBody VolunteerDto value){
        Map<String, Object> response = new HashMap<>();

        Volunteer volunteer = volunteerService.create(value);

        if(volunteer != null){
            response.put("result", "SUCCESS");
            response.put("user", new VolunteerDto(volunteer));
        }else {
            response.put("result", "FAIL");
            response.put("reason", "회원가입 실패");
        }
        return response;
    }

    @ApiOperation(value="봉사자 정보 수정", notes="봉사자 id와 자기소개를 입력받아 그대로 내용 수정")
    @PutMapping("/")
    public Map<String, Object> updateVolunteer(@RequestParam Long vid, @RequestParam String introduce){
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Volunteer> oVolunteer = volunteerService.findById(vid);
            if (oVolunteer.isPresent()) {
                Volunteer volunteer = oVolunteer.get();
                volunteer.setIntroduce(introduce);
                Volunteer result = volunteerService.save(volunteer);
                response.put("result", "SUCCESS");
                response.put("user", new VolunteerDto(volunteer));
            } else {
                throw new Exception("일치하는 회원정보가 존재하지 않습니다.");
            }
        }catch (Exception e){
            System.out.println("에러 메세지 : " + e.getMessage());
            e.printStackTrace();
            response.put("result", "FAIL");
            response.put("reason", e.getMessage());
        }
        return response;
    }

    @ApiOperation(value="봉사자 정보 삭제", notes="봉사자 정보를 받아 삭제")
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteVolunteer(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();

        try{
            Optional<Volunteer> Volunteer = volunteerService.findById(id);
            if(Volunteer.isPresent()){
                volunteerService.delete(Volunteer.get());
                response.put("result", "SUCCESS");
            }else{
                throw new Exception("일치하는 회원정보가 존재하지 않습니다.");
            }
        }catch (Exception e){
            System.out.println("에러 메세지 : " + e.getMessage());
            e.printStackTrace();
            response.put("result", "FAIL");
            response.put("reason", "해당 봉사자가 존재하지 않습니다.");
        }
        return response;
    }
}
