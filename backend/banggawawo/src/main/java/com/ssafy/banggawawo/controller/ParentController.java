package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.ParentDto;
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

import java.util.*;

@Api(value = "학부모 관련 정보 처리")
@RequiredArgsConstructor
@RequestMapping(value = "/parent", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class ParentController {
    private final StudentService studentService;
    private final JwtService jwtService;

    @ApiOperation(value="학부모 로그인", notes="학부모 정보를 입력받아 로그인 후 JWT 토큰 발급")
    @PostMapping("")
    public Map<String, Object> loginParent(@RequestParam String email, @RequestParam String password){
        Map<String, Object> response = new HashMap<>();

        try{
            List<Student> childs = studentService.findByPemail(email);
            if(childs.size()>0){
                if(childs.get(0).getPpw().equals(password)){
                    response.put("result", "SUCCESS");
                    Student student = childs.get(0);
                    response.put("JWT", jwtService.create("PARENT", new ParentDto(student.getPemail(), student.getPpw())));
                }else{
                    throw new Exception("비밀번호가 일치하지 않습니다.");
                }
            }else{
                throw new Exception("이메일이 존재하지 않습니다.");
            }
        }catch (Exception e){
            System.out.println("에러 메세지 : " + e.getMessage());
            e.printStackTrace();
            response.put("result", "FAIL");
            response.put("reason", e.getMessage());
        }
        return response;
    }

    // 리턴값 : 자식객체 리스트
    @ApiOperation(value="자녀 목록 조회", notes="이메일(pemail)를 입력받아 자녀 리스트 제공")
    @GetMapping("/{email}")
    public Map<String, Object> findByParentEmail(
            @ApiParam(value="부모 email", required=true, example="parents@ssafy.com")
            @PathVariable("email") String email){
        Map<String, Object> response = new HashMap<>();
        try{
            List<Student> childList = studentService.findByPemail(email);
            System.out.println(childList.getClass());
            List<StudentDto> childs = new ArrayList<StudentDto>();
            for(Student s : childList){
                childs.add(new StudentDto(s));
            }
            response.put("result", "SUCCESS");
            response.put("childs", childs);
        }catch (Exception e){
            System.out.println("에러 메세지 : " + e.getMessage());
            e.printStackTrace();
            response.put("result", "FAIL");
            response.put("reason",  e.getMessage());

        }
        return response;
    }

    @ApiOperation(value="학부모 비밀번호 수정", notes="학부모 이메일과 변경할 비밀번호를 입력받아 비밀번호 수정")
    @PutMapping("")
    public Map<String, Object> updateStudent(@RequestParam String email, @RequestParam String password){
        Map<String, Object> response = new HashMap<>();
        try{
            if(studentService.updatePpw(email, password)==0) throw new Exception("비밀번호 변경 실패");
            response.put("result", "SUCCESS");
        }catch(Exception e){
            System.out.println("에러 메세지 : " + e.getMessage());
            e.printStackTrace();
            response.put("result", "FAIL");
            response.put("reason", e.getMessage());
        }
        return response;
    }

}