package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.StudentDto;
import com.ssafy.banggawawo.domain.dto.StudentFrontDto;
import com.ssafy.banggawawo.domain.dto.VolunteerDto;
import com.ssafy.banggawawo.domain.dto.VolunteerFrontDto;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import com.ssafy.banggawawo.service.JwtService;
import com.ssafy.banggawawo.service.StudentService;
import com.ssafy.banggawawo.service.VolunteerService;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/kakao")
public class AuthController {

    @Value("${KAKAO_API_KEY}")
    private String KAKAO_API_KEY;
    @Value("${REDIRECT_URL}")
    private String REDIRECT_URL;

    @Autowired
    private StudentService studentService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private JwtService jwtService;

    // For BackEnd Test. Not used in FrontEnd -------------------------------------------------
//    @GetMapping("/oauth")
//    public String kakaoConnect() {
//        StringBuffer url = new StringBuffer();
//        url.append("https://kauth.kakao.com/oauth/authorize?");
//        url.append("client_id=" + KAKAO_API_KEY);
//        url.append("&redirect_uri=" + REDIRECT_URL);
//        url.append("&response_type=code");
//
//        return "redirect:" + url;
//    }
//
//    @RequestMapping(value = "/callback", produces = "application/json", method = {RequestMethod.GET,
//            RequestMethod.POST})
//    public Map<String, Object> kakaoLogin(@RequestParam("code") String code,
//                           HttpSession session) throws IOException {
//        String accessToken = getAccessToken(code);
////        session.setAttribute("access_token", accessToken); // 로그아웃할 때 사용
//        getUserInfo(accessToken); // 사용자 정보 받아오기
//
//        Map<String, Object> response = verifyUser(code);
//        return response;
//    }
    // -----------------------------------------------------------------------------------------

    @ApiOperation(value="회원 여부 확인",
            notes="카카오 인가코드를 입력받아 존재하는 회원정보인지 확인한다. \n " +
                    "회원인 경우(isUser==true) 회원정보가 포함된 JWT토큰을 반환하고, 비회원의 경우 kakao에서 수집한 별명, 연령대, 회원번호(kakaoId) 반환.")
    @GetMapping("/login/{code}")
    public Map<String, Object> verifyUser(@PathVariable("code") String code) {
        Map<String, Object> response = new HashMap<>();

        try{
            String accessToken = getAccessToken(code);
            System.out.println("accessToken = " + accessToken);

            Map<String, Object> userInfo = getUserInfo(accessToken);

            String kakaoId = userInfo.get("kakaoId") + "";
            Map<String, Object> result = findByKakaoId(kakaoId);
            if((boolean) result.get("result")){
                response.put("result", "SUCCESS");
                response.put("isUser", true);
                response.put("accessToken", accessToken);
                String userType = (String) result.get("userType");
                if(userType.equals("STUDENT")) {
                    Student user = (Student) result.get("user");
                    user.setPpw("");
                    StudentFrontDto studentFrontDto = new StudentFrontDto(new StudentDto(user));
                    response.put("JWT", jwtService.create("STUDENT", studentFrontDto)); //JWT 발급
                }else if(userType.equals("VOLUNTEER")){
                    VolunteerFrontDto volunteerFrontDto = new VolunteerFrontDto(new VolunteerDto((Volunteer) result.get("user")));
                    response.put("JWT", jwtService.create("VOLUNTEER", volunteerFrontDto)); //JWT 발급
                }else{
                    throw new Exception("학생도 봉사자도 아닌 회원의 kakao 로그인 시도. 이 에러가 나올수 없음");
                }
            }else{
                userInfo.put("result", "SUCCESS");
                userInfo.put("isUser", false);
                userInfo.put("accessToken", accessToken);
                return userInfo;
            }
        }catch (Exception e){
            response.put("result", "FAIL");
            response.put("reason", e.getMessage());
        }
        return response;
    }

    // 카카오 서버에 인가코드 전송 -> access Tocken 응답
    private String getAccessToken(String code) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        JSONObject response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", KAKAO_API_KEY)
                        .queryParam("redirect_uri", REDIRECT_URL)
                        .queryParam("code", code)
                        .build())
                .retrieve().bodyToMono(JSONObject.class).block();

        return (String) response.get("access_token");
    }

    // 2. 카카오 서버에 액세스 토큰 전송 -> 사용자 정보 응답
    private Map<String, Object> getUserInfo(String accessToken){
        WebClient webClient = WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        JSONObject response = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/v2/user/me").build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve().bodyToMono(JSONObject.class).block();

        System.out.println("response=================\n" + response);

        Long id = (Long) response.get("id");
        Map<String, Object> kakao_account = (Map<String, Object>) response.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");

        Map<String, Object> result = new HashMap<>();

        result.put("kakaoId", response.get("id"));
        result.put("nickname", profile.get("nickname")) ;
        result.put("ageRange", ((String) kakao_account.get("age_range")).substring(0,2));

        return result;
    }

    // 3. 회원 여부 확인
    public Map<String, Object> findByKakaoId(String kakaoId){
        Map<String, Object> response = new HashMap<>();

        Optional<Student> oStudent = studentService.findByKakaoId(kakaoId);
        if(oStudent.isPresent()){
            response.put("result", true);
            response.put("userType", "STUDENT");
            response.put("user", oStudent.get());
        }else{
            Optional<Volunteer> oVolunteer = volunteerService.findByKakaoId(kakaoId);
            if(oVolunteer.isPresent()){
                response.put("result", true);
                response.put("userType", "VOLUNTEER");
                response.put("user", oVolunteer.get());
            }else{
                response.put("result", false);
                response.put("reason", "존재하지 않는 토큰");
            }
        }
        return response;
    }

    @ApiOperation(value="카카오 토큰 만료",
            notes="로그인 시 발행한 카카오 토큰을 만료시킨다. 로그아웃 할 때 사용한다.")
    @GetMapping("/logout/{accessToken}")
    public Map<String, Object> logout(@PathVariable("accessToken") String accessToken) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        Map<String, Object> response = new HashMap<>();

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
            response.put("result", "SUCCESS");
        } catch (IOException e) {
            response.put("result", "FAIL");
            response.put("reason", e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @ApiOperation(value="카카오 연결 끊기",
            notes="사용자 계정과 카카오 연결을 끊는다. 회원 탈퇴 시 사용하며, 이후 정보제공 동의를 다시 받아야 한다.")
    @GetMapping("/delete/{accessToken}")
    public Map<String, Object> unlink(@PathVariable("accessToken") String accessToken) {
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";
        Map<String, Object> response = new HashMap<>();
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
            response.put("result", "SUCCESS");
        } catch (IOException e) {
            response.put("result", "FAIL");
            response.put("reason", e.getMessage());
            e.printStackTrace();
        }
        return  response;
    }
}
