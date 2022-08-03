package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.StudentDto;
import com.ssafy.banggawawo.domain.dto.VolunteerDto;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import com.ssafy.banggawawo.service.StudentService;
import com.ssafy.banggawawo.service.VolunteerService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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

    // 카카오 토큰으로 유저 확인
    @GetMapping("/{token}")
    @ApiOperation(value="유저 확인", notes="카카오 토큰을 입력받아 존재하는 회원정보인지 확인 후, 존재할 경우 회원정보 불러옴")
    public Map<String, Object> findByToken(@PathVariable("token") String token){
        Map<String, Object> response = new HashMap<>();

        Optional<Student> oStudent = studentService.findByToken(token);
        if(oStudent.isPresent()){
            response.put("result", "SUCCESS");
            response.put("type", "STUDENT");
            response.put("user", oStudent.get());
        }else{
            Optional<Volunteer> oVolunteer = volunteerService.findByToken(token);
            if(oVolunteer.isPresent()){
                response.put("result", "SUCCESS");
                response.put("type", "VOLUNTEER");
                response.put("user", oVolunteer.get());
            }else{
                response.put("result", "FAIL");
                response.put("reason", "존재하지 않는 토큰");
            }
        }
        return response;
    }

    // Student ///////////////////////////////////////////////////////////////////////////////////////////////
    // 학생 정보 조회
    @GetMapping("/student/{id}")
    public Map<String, Object> findByStudentId(@PathVariable("sId") Long id){
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


    // 학생 회원가입
    @PostMapping("/student")
    public Map<String, Object> saveStudent(@RequestBody StudentDto value){
        Map<String, Object> response = new HashMap<>();

        // 부모 이메일로 임시 비밀번호 발급 후 전송
        //

        // value.setS_ppw(발급한 임시 비밀번호);
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

    // 학생 정보 수정
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

    // 학생 정보 삭제
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
    @GetMapping("/volunteer/{id}")
    public Map<String, Object> findByVolunteerId(@PathVariable("vId") Long id){
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
}
