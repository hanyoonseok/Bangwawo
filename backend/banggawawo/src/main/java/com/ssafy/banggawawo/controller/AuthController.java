package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import com.ssafy.banggawawo.service.JwtService;
import com.ssafy.banggawawo.service.StudentService;
import com.ssafy.banggawawo.service.VolunteerService;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    private String KAKAO_API_KEY = "301c3457789fa54edf6d5263bc80b62a";
    private String REDIRECT_URL = "http://localhost:8080/api/kakao/callback";

    @Autowired
    private StudentService studentService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/oauth")
    public String kakaoConnect() {
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + KAKAO_API_KEY);
        url.append("&redirect_uri=" + REDIRECT_URL);
        url.append("&response_type=code");

        return "redirect:" + url;
    }

//    @RequestMapping(value = "/callback", produces = "application/json", method = {RequestMethod.GET,
//            RequestMethod.POST})
//    public void kakaoLogin(@RequestParam("code") String code,
//                           HttpSession session) throws IOException {
//
//        String accessToken = getKakaoAccessToken(code);
//        session.setAttribute("access_token", accessToken); // 로그아웃할 때 사용
//
//        getKakaoUserInfo(accessToken); // 사용자 정보 받아오기
//    }

    // 인가코드 : 3VVzGf6ij6F6pDV-Nc9KDaiATpHIt7UJ6AtSiX8cOr2hWuQsgJCrpH1HbYaoNJytJmr1aQopcBMAAAGCbFteVg
    @ApiOperation(value="회원 여부 확인",
            notes="카카오 인가코드를 입력받아 존재하는 회원정보인지 확인한다 \n " +
                    "회원인 경우 회원정보(회원타입, 회원Id)를 JWT와 함께 발급하고, 비회원의 경우 kakao에서 수집한 별명, 연령대, 회원번호(token) 반한")
    @GetMapping("/{code}")
    public Map<String, Object> getKakaoAccessToken(@RequestParam("code") String code) {
        Map<String, Object> response = new HashMap<>();

        String accessToken = getAccessToken(code);
        Map<String, Object> userInfo = getUserInfo(accessToken);

        String token = (String)userInfo.get("token");
        Map<String, Object> result = findByToken(token);
        if((boolean) result.get("result")){
            response.put("isUser", true);
            response.put("JWT",
                    jwtService.create((String) result.get("userType"),
                            (String) result.get("userId"))); //JWT 발급
            response.put("user", result.get("user"));
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

        result.put("token", (String) kakao_account.get("id"));
        result.put("nickname", (String) profile.get("nickname")) ;
        result.put("ageRange", ((String) kakao_account.get("age_range")).substring(0,1));

        return result;
    }

    public Map<String, Object> findByToken(String token){
        Map<String, Object> response = new HashMap<>();

        Optional<Student> oStudent = studentService.findByToken(token);
        if(oStudent.isPresent()){
            response.put("result", true);
            response.put("userType", "STUDENT");
            response.put("userId", oStudent.get().getSId());
        }else{
            Optional<Volunteer> oVolunteer = volunteerService.findByToken(token);
            if(oVolunteer.isPresent()){
                response.put("result", true);
                response.put("userType", "VOLUNTEER");
                response.put("userId", oVolunteer.get().getVId());
            }else{
                response.put("result", false);
                response.put("reason", "존재하지 않는 토큰");
            }
        }
        return response;
    }

}
