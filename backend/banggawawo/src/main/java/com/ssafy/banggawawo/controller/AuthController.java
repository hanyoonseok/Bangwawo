package com.ssafy.banggawawo.controller;

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
////        String accessToken = getAccessToken(code);
//////        session.setAttribute("access_token", accessToken); // 로그아웃할 때 사용
////        getUserInfo(accessToken); // 사용자 정보 받아오기
//
//        Map<String, Object> response = verifyUser(code);
//        return response;
//    }
    // -----------------------------------------------------------------------------------------

    @ApiOperation(value="회원 여부 확인",
            notes="카카오 인가코드를 입력받아 존재하는 회원정보인지 확인한다. \n " +
                    "회원인 경우(isUser==true) 회원정보가 포함된 JWT토큰을 반환하고, 비회원의 경우 kakao에서 수집한 별명, 연령대, 회원번호(kakaoId) 반환.")
    @GetMapping("/{code}")
    public Map<String, Object> verifyUser(@PathVariable("code") String code) {
        System.out.println("제대로 들어옴");
        Map<String, Object> response = new HashMap<>();

            String accessToken = getAccessToken(code);
            Map<String, Object> userInfo = getUserInfo(accessToken);

            String kakaoId = userInfo.get("kakaoId") + "";
            Map<String, Object> result = findByKakaoId(kakaoId);
            if((boolean) result.get("result")){
                response.put("isUser", true);
                response.put("JWT",
                        jwtService.create((String) result.get("userType"),
                                 result.get("user"))); //JWT 발급
            }else{
                userInfo.put("isUser", false);
                return userInfo;
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

}
