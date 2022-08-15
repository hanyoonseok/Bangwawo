package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.dto.VolunteerDto;
import com.ssafy.banggawawo.domain.dto.VolunteerFrontDto;
import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.service.ClassService;
import com.ssafy.banggawawo.service.EnrolService;
import com.ssafy.banggawawo.service.LikesService;
import com.ssafy.banggawawo.service.RequestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/class")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @Autowired
    private RequestService requestservice;

    @Autowired
    private LikesService likeService;

    @Autowired
    private EnrolService enrolService;


    @ApiOperation(value = "수업 생성")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ClassDto classDto) throws Exception {
        return new ResponseEntity<>(classService.save(classDto), HttpStatus.OK);
    }

    @ApiOperation(value = "개설 요청에 대한 수업 생성")
    @PostMapping("/{rid}")
    public ResponseEntity<?> save(@RequestBody ClassDto classDto, @PathVariable("rid") Long rid) throws Exception {
        Optional<Request> orequest = requestservice.readonly(rid);
        classDto.setRId(rid);
        ClassDto cresult = classService.save(classDto);

        if (orequest.isPresent()) {
            orequest.get().setSolved(true);
            requestservice.update(orequest.get());
        }
        List<Likes> result = likeService.likesList(rid);

        for (int i = 0; i < result.size(); i++) {
            //특정 수업이 개설되면 위수업을 듣고 싶어했던 모든 학생들의 opened를 (false->true)로 바꿔 준다.
            result.get(i).setLOpened(true);
            likeService.updateboolean(result.get(i));
        }

        return new ResponseEntity<>(cresult, HttpStatus.OK);
    }

    @ApiOperation(value = "이미지 등록")
    @PostMapping("/image")
    public ResponseEntity<String> fileImage(@RequestParam(name = "thumbnail") MultipartFile multipartFile) throws Exception {
        String encodedImage = Base64.getEncoder().encodeToString(multipartFile.getBytes());
        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        return new ResponseEntity<>("data:image/"+ext+";base64,"+encodedImage, HttpStatus.OK);
    }

    @ApiOperation(value = "수업 수정")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ClassDto classDto) throws Exception {
        return new ResponseEntity<>(classService.update(classDto), HttpStatus.OK);
    }

    @ApiOperation(value = "수업 리스트 가져오기 (검색가능)", notes = "title : 제목검색, opened : 공개수업여부, state : 수업상태 [0:진행전, 1:진행중, 2:종료], vid : 봉사자 ID로 검색 ")
    @GetMapping
    public ResponseEntity<List<ClassDto>> findAll(@RequestParam(required = false, name = "title") String title,
                                                  @RequestParam(required = false, name = "opened") Boolean opened,
                                                  @RequestParam(required = false, name = "state") Integer state,
                                                  @RequestParam(required = false, name = "vid") Long vid) throws Exception{
        ClassDto classDto = new ClassDto();
        classDto.setSearchfQuery(title, opened, state, vid);
        List<ClassDto> list = classService.findAll(classDto);
        list = list.stream().map(dto -> {
            Long tmp = null;
            try{
                tmp = enrolService.countEnrolsByClassId(dto.getCId());
            }catch(Exception e){
                tmp = 0L;
            }
            dto.setEnrolcnt(Integer.parseInt(Long.toString(tmp)));
            return dto;
        }).collect(Collectors.toList());
        return new ResponseEntity<List<ClassDto>>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "수업 하나 가져오기", notes="수업id(cid)를 받아 수업 정보를 불러온다.\n 반환값 : class(수업정보), vCharacter(봉사자캐릭터정보)")
    @GetMapping("/{id}")
    public ResponseEntity<?> findByCId(@PathVariable("id") Long id) throws Exception {
        Map<String, Object> response = new HashMap<String, Object>();
        ClassDto classDto = classService.findByCId(id);
        classDto.setEnrolcnt(Integer.parseInt(enrolService.countEnrolsByClassId(classDto.getCId())+""));
        response.put("class", classDto);
        VolunteerFrontDto volunteerFrontDto = new VolunteerFrontDto(new VolunteerDto(classDto.getVId()));
        response.put("vCharacter", volunteerFrontDto.getCharacter());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "수업 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        if(enrolService.deleteByClassId(id) && classService.deleteById(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
