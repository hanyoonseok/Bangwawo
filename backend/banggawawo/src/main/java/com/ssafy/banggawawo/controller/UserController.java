package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.StudentDto;
import com.ssafy.banggawawo.domain.dto.VolunteerDto;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import com.ssafy.banggawawo.service.StudentService;
import com.ssafy.banggawawo.service.VolunteerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class UserController {
    private final StudentService studentService;
    private final VolunteerService volunteerService;

    // Student ///////////////////////////////////////////////////////////////////////////////////////////////
    @ApiOperation(value="학생 정보 조회", notes="학생 id(sid)를 받아 학생 정보를 제공한다.")
    @GetMapping("/student/{id}")
    public Map<String, Object> findByStudentId(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();
        Optional<Student> oStudent = studentService.findById(id);
        if(oStudent.isPresent()){
            response.put("result", "SUCCESS");
            response.put("type", "STUDENT");
            response.put("user", oStudent.get());
        }else{
            response.put("result", "FAIL");
            response.put("reason", "존재하지 않는 토큰");
        }
        return response;
    }


    @ApiOperation(value="학생 화원가입", notes="학생 정보를 입력받아 회원가입을 진행한다.")
    @PostMapping("/student")
    public Map<String, Object> saveStudent(@RequestBody StudentDto value){
        Map<String, Object> response = new HashMap<>();

        // 부모 이메일로 임시 비밀번호 발급 후 전송
        String parentsPassword = "";
        for (int i = 0; i < 8; i++) {
            int rndVal = (int) (Math.random() * 62);
            if (rndVal < 10) {
                parentsPassword += rndVal;
            } else if (rndVal > 35) {
                parentsPassword += (char) (rndVal + 61);
            } else {
                parentsPassword += (char) (rndVal + 55);
            }
        }

        value.setPpw(parentsPassword);
        Student student = studentService.save(value);

        if(student != null){
            response.put("result", "SUCCESS");
            response.put("user", student);
        }else {
            response.put("result", "FAIL");
            response.put("reason", "회원가입 실패");
        }
        return response;
    }

    @ApiOperation(value="학생 정보 수정", notes="학생 정보를 입력받아 그대로 내용 수정")
    @PutMapping("/student")
    public Map<String, Object> updateStudent(@RequestBody StudentDto value){
        Map<String, Object> response = new HashMap<>();

        Optional<Student> oStudent = studentService.findById(value.getSId());
        if(oStudent.isPresent()){
            Student student = studentService.save(value);
            response.put("result", "SUCCESS");
            response.put("user", student);
        }else{
            response.put("result", "FAIL");
            response.put("reason", "해당 학생이 존재하지 않습니다.");
        }
        return response;
    }

    @ApiOperation(value="학생 정보 삭제", notes="해당 학생 정보를 받아 삭제한다")
    @DeleteMapping("/student")
    public Map<String, Object> deleteStudent(@ RequestBody StudentDto value){
        Map<String, Object> response = new HashMap<>();
;
        Optional<Student> student = studentService.findById(value.getSId());
        if(student.isPresent()){
            studentService.delete(student.get());
            response.put("result", "SUCCESS");
        }else{
            response.put("result", "FAIL");
            response.put("reason", "해당 학생이 존재하지 않습니다.");
        }

        return response;
    }

    // Volunteer ///////////////////////////////////////////////////////////////////////////////////////////////
    // 봉사자 정보 조회
    @ApiOperation(value="유저 확인", notes="")
    @GetMapping("/volunteer/{id}")
    public Map<String, Object> findByVolunteerId(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();
        Optional<Volunteer> oVolunteer = volunteerService.findById(id);
        if(oVolunteer.isPresent()){
            response.put("result", "SUCCESS");
            response.put("type", "VOLUNTEER");
            response.put("user", oVolunteer.get());
        }else{
            response.put("result", "FAIL");
            response.put("reason", "존재하지 않는 토큰");
        }
        return response;
    }

    // 봉사자 회원가입
    @PostMapping("/volunteer")
    public Map<String, Object> saveVolunteer(@RequestBody VolunteerDto value){
        Map<String, Object> response = new HashMap<>();

        Volunteer volunteer = volunteerService.save(value);

        if(volunteer != null){
            response.put("result", "SUCCESS");
            response.put("user", volunteer);
        }else {
            response.put("result", "FAIL");
            response.put("reason", "회원가입 실패");
        }
        return response;
    }

    // 봉사자 정보 수정
    @PutMapping("/volunteer")
    public Map<String, Object> updateVolunteer(@RequestBody VolunteerDto value){
        Map<String, Object> response = new HashMap<>();

        Optional<Volunteer> oVolunteer = volunteerService.findById(value.getVId());
        if(oVolunteer.isPresent()){
            Volunteer volunteer = volunteerService.save(value);
            response.put("result", "SUCCESS");
            response.put("user", volunteer);
        }else{
            response.put("result", "FAIL");
            response.put("reason", "해당 봉사자가 존재하지 않습니다.");
        }
        return response;
    }

    // 봉사자 정보 삭제
    @DeleteMapping("/volunteer")
    public Map<String, Object> deleteVolunteer(@ RequestBody VolunteerDto value){
        Map<String, Object> response = new HashMap<>();
        ;
        Optional<Volunteer> Volunteer = volunteerService.findById(value.getVId());
        if(Volunteer.isPresent()){
            volunteerService.delete(Volunteer.get());
            response.put("result", "SUCCESS");
        }else{
            response.put("result", "FAIL");
            response.put("reason", "해당 봉사자가 존재하지 않습니다.");
        }

        return response;
    }
}
