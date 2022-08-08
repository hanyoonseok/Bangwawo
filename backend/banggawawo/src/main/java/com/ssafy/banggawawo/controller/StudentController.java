package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.StudentDto;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.service.JwtService;
import com.ssafy.banggawawo.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Api(value = "학생 관련 정보 처리")
@RequiredArgsConstructor
@RequestMapping(value = "/student", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class StudentController {

    private final StudentService studentService;
    private final JwtService jwtService;

    @ApiOperation(value="학생 정보 조회", notes="학생 id(sId)를 입력받아 학생 정보 제공")
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
                response.put("user", new StudentDto(oStudent.get()));
            }else{
                throw new Exception("일치하는 회원정보가 존재하지 않습니다.");
            }
        }catch (Exception e){
            System.out.println("에러 메세지 : " + e.getMessage());
            e.printStackTrace();
            response.put("result", "FAIL");
            response.put("reason", "존재하지 않는 id");

        }
        return response;
    }


    @ApiOperation(value="학생 회원가입", notes="학생 정보를 입력받아 회원가입 후 JWT 토큰 발급")
    @PostMapping("")
    public Map<String, Object> saveStudent(@RequestBody StudentDto value){
        Map<String, Object> response = new HashMap<>();

        try{
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
            StudentDto student = new StudentDto(studentService.create(value));

            response.put("result", "SUCCESS");
            response.put("JWT", jwtService.create("STUDENT", student));

        }catch(Exception e){
            response.put("result", "FAIL");
            response.put("reason", "회원가입 실패");
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value="학생 정보 수정", notes="학생 정보를 입력받아 그대로 내용 수정")
    @PutMapping("")
    public Map<String, Object> updateStudent(@RequestParam Long sid, @RequestParam String nickname){
        Map<String, Object> response = new HashMap<>();
        try{
            Optional<Student> oStudent = studentService.findById(sid);
            if(oStudent.isPresent()){
//                System.out.println("sId = " + oStudent.get().getSId());
//                StudentDto student = new StudentDto(oStudent.get());
//                student.setNickname(nickname);
//                System.out.println("닉네임 입력 후 sId = " + student.getSId());
//                System.out.println("닉네임 입력 후 nickname = " + student.getNickname());
                oStudent.get().setNickname(nickname);
                Student result = studentService.save(oStudent.get());
                response.put("result", "SUCCESS");
//                response.put("user", new StudentDto(result));
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
