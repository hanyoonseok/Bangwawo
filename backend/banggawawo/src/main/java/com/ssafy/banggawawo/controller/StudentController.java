package com.ssafy.banggawawo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.banggawawo.domain.dto.*;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import com.ssafy.banggawawo.service.JwtService;
import com.ssafy.banggawawo.service.MailService;
import com.ssafy.banggawawo.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api(value = "학생 관련 정보 처리")
@RequiredArgsConstructor
@RequestMapping(value = "/student", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class StudentController {

    private final StudentService studentService;
    private final MailService mailService;
    private final JwtService jwtService;

    @ApiOperation(value="학생 정보 조회", notes="학생 id(id)를 입력받아 학생 정보 제공")
    @GetMapping("/{id}")
    public Map<String, Object> findByStudentId(
            @ApiParam(value="학생 id", required=true, example="1")
            @PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            Optional<Student> oStudent = studentService.findById(id);
            if(oStudent.isPresent()){
                response.put("result", "SUCCESS");
                response.put("type", "STUDENT");
                response.put("user", new StudentFrontDto(new StudentDto(oStudent.get())));
            }else{
                throw new Exception("일치하는 회원정보가 존재하지 않습니다.");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("result", "FAIL");
            response.put("reason", e.getMessage());

        }
        return response;
    }

    @ApiOperation(value="학생 회원가입", notes="학생 정보를 입력받아 회원가입 후 JWT 토큰 발급")
    @PostMapping("")
    public Map<String, Object> saveStudent(@RequestBody StudentFrontDto value){
        Map<String, Object> response = new HashMap<>();
        try{
            List<Student> childs = studentService.findByPemail(value.getPemail());
            if(childs.size()>0) {
                // 부모 계정이 이미 존재하는 경우
                value.setPpw(childs.get(0).getPpw());
            }else{
                // 새 부모 계정일 경우, 임시 비밀번호 발급 후 이메일 전송
                MailDto mail = mailService.createMailAndChangePassword(value.getPemail(), value.getNickname());
                value.setPpw(mail.getTmpPassword());
                mailService.mailSend(mail);
            }
            StudentDto student = new StudentDto(studentService.create(new StudentDto(value)));

            student.setPpw("");
            response.put("result", "SUCCESS");
            response.put("JWT", jwtService.create("STUDENT", new StudentFrontDto(student)));

        }catch(Exception e){
            response.put("result", "FAIL");
            response.put("reason", "회원가입 실패");
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value="학생 별명 수정", notes="학생 id와 별명을 입력받아 그대로 내용 수정\nid : 학생아이디, nickname : 수정할 별명")
    @PutMapping("/nickname")
    public Map<String, Object> updateNickname(@RequestBody Map<String, Object> request){
        Long id = Long.parseLong(request.get("id").toString());
        String nickname = request.get("nickname").toString();

        Map<String, Object> response = new HashMap<>();
        try{
            Optional<Student> oStudent = studentService.findById(id);
            if(oStudent.isPresent()){
                oStudent.get().setNickname(nickname);
                Student result = studentService.save(oStudent.get());
                response.put("result", "SUCCESS");
            }else {
                throw new Exception("일치하는 회원정보가 존재하지 않습니다.");
            }
        }catch(Exception e){
            System.out.println("에러 메세지 : " + e.getMessage());
            e.printStackTrace();
            response.put("result", "FAIL");
            response.put("reason", e.getMessage());
        }
        return response;
    }

    @ApiOperation(value="학생 캐릭터 수정", notes="학생 정보를 입력받아 그대로 내용 수정\nid : 학생아이디, character : 수정할 캐릭터 정보")
    @PutMapping("/character")
    public Map<String, Object> updateCharacter(@RequestBody Map<String, Object> request){
        Long id = Long.parseLong(request.get("id").toString());
        ObjectMapper mapper = new ObjectMapper();
        ColorDto[] character = mapper.convertValue(request.get("character"), new TypeReference<List<ColorDto>>() {}).toArray(new ColorDto[0]);

        Map<String, Object> response = new HashMap<>();
        try{
            Optional<Student> oStudent = studentService.findById(id);
            if(oStudent.isPresent()){
                Student student = oStudent.get();
                student.setCharacter(studentService.toClass(character));
                Student result = studentService.save(student);
                response.put("result", "SUCCESS");
                response.put("user", new StudentFrontDto(new StudentDto(result)));
            }else {
                throw new Exception("일치하는 회원정보가 존재하지 않습니다.");
            }
        }catch(Exception e){
            System.out.println("에러 메세지 : " + e.getMessage());
            e.printStackTrace();
            response.put("result", "FAIL");
            response.put("reason", e.getMessage());
        }
        return response;
    }

    @ApiOperation(value="학생 정보 삭제", notes="학생 정보를 받아 삭제")
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteStudent(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();

        try{
            Optional<Student> student = studentService.findById(id);
            if(student.isPresent()){
                studentService.delete(student.get());
                response.put("result", "SUCCESS");
            }else{
                throw new Exception("일치하는 회원정보가 존재하지 않습니다.");
            }
        }catch (Exception e){
            System.out.println("에러 메세지 : " + e.getMessage());
            e.printStackTrace();
            response.put("result", "FAIL");
            response.put("reason", "해당 학생이 존재하지 않습니다.");
        }
        return response;
    }
}
