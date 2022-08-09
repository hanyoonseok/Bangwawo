package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.dto.EnrolDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import com.ssafy.banggawawo.service.ClassService;
import com.ssafy.banggawawo.service.EnrolService;
import com.ssafy.banggawawo.service.StudentService;
import com.ssafy.banggawawo.service.VolunteerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/session")
public class OpenviduController {

    @Autowired
    private ClassService classService;
    @Autowired
    private EnrolService enrolService;

    private Map<String, String> mapSessions = new ConcurrentHashMap<>();
    private Map<String, Map<String, String>> mapSessionNamesTokens = new ConcurrentHashMap<>();

    @ApiOperation("학생 - 해당 수업 들을 수 있나 확인 후 세션 ID 돌려줌")
    @GetMapping("/class/join/{cid}/{sid}")
    public ResponseEntity<String> joinClassSession(@PathVariable("cid") Long cid,
                                                   @PathVariable("sid") Long sid) throws Exception {
        //cid로 클래스 데이터
        ClassDto classDto = classService.findByCId(cid);
        // open여부 구분 후
        if(!classDto.getOpened()){
            //false면 해당 수업 들을 수 있는지 확인하기
            EnrolDto enrolDto = enrolService.findByClassAndStudent(cid, sid);
            //없으면 못들어
            if(enrolDto == null || enrolDto.getEId() == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        // 세션 존재 여부 확인 후
        String cidStr = "Class"+cid;
        if(mapSessions.containsKey(cidStr)){
            // 인원 확인 후
            if(mapSessionNamesTokens.get(cidStr).size() >= classDto.getMaxcnt()){
                //넘치면 못들어
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
                // 세션 돌려주기
                try{
                    this.mapSessionNamesTokens.get(cidStr).put(sid+"", "student");
                    return new ResponseEntity<>(mapSessions.get(cidStr), HttpStatus.OK);
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
       //학생일때는 못들어가
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    @ApiOperation("봉사자 - 세션 만들기")
    @GetMapping("/class/start/{cid}/{vid}")
    public ResponseEntity<String> makeClassSession(@PathVariable("cid") Long cid,
                                             @PathVariable("vid") Long vid) throws Exception {
        // 세션 만들어주기
        //cid로 클래스 데이터
        ClassDto classDto = classService.findByCId(cid);
        String cidStr = "Class"+cid;

        //해당 봉사자가 해당 클래스 만든 경우에만 세션 작성
        // + 아직 만든적 없는 세션일 경우에만 세션 작성
        if(classDto.getVId().getVId() == vid && !classDto.getState()){
            try {
                this.mapSessions.put(cidStr, cid+cidStr);
                this.mapSessionNamesTokens.put(cidStr, new ConcurrentHashMap<>());
                this.mapSessionNamesTokens.get(cidStr).put(vid+"", "volunteer");

                return new ResponseEntity<>(mapSessions.get(cidStr), HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //네가 만든 클래스 아니면 돌아가
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("봉사자 - 수업 종료하기 (수업 status 변경)")
    @GetMapping("/class/close/{cid}/{vid}")
    public ResponseEntity<?> closeClassSession(@PathVariable("cid") Long cid,
                                               @PathVariable("vid") Long vid) throws Exception {
        ClassDto classDto = classService.findByCId(cid);
        String cidStr = "Class"+cid;
        //세션 지워주기
        if(classDto.getVId().getVId() == vid){
            mapSessionNamesTokens.remove(cidStr);
            mapSessions.remove(cidStr);

            //사용된 클래스 처리
            classDto.setState(true);
            classService.update(classDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("비밀친구 세션")
    @GetMapping("/secret/{sid}")
    public ResponseEntity<String> joinSecretSession(@PathVariable(value = "sid") Long sid) throws Exception {
        // 세션 존재 여부 확인 후
        String sidStr = "Secret"+sid;
        if(mapSessions.containsKey(sidStr)){
            // 2명 (봉사자 + 학생) 확인 후
            if(mapSessionNamesTokens.get(sidStr).size() == 2){
                //넘으면 못들어가
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            // 세션 돌려주기
            try{
                this.mapSessionNamesTokens.get(sidStr).put(sid+"Volunteer", "volunteer");
                return new ResponseEntity<>(mapSessions.get(sidStr), HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>(null, HttpStatus.MULTI_STATUS);
            }
        }else{
            // 세션 만들어주기
            try {
                this.mapSessions.put(sidStr, sid+sidStr);
                this.mapSessionNamesTokens.put(sidStr, new ConcurrentHashMap<>());
                this.mapSessionNamesTokens.get(sidStr).put(sid+"", "student");

                return new ResponseEntity<>(mapSessions.get(sidStr), HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.MULTI_STATUS);
            }
        }
    }
}
