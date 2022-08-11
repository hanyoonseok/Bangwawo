package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.domain.entity.Request;
import com.ssafy.banggawawo.service.ClassService;
import com.ssafy.banggawawo.service.LikesService;
import com.ssafy.banggawawo.service.RequestService;
import com.ssafy.banggawawo.util.FileUploadUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/class")
public class ClassController {
    private final ClassService classService;
    @Autowired
    public ClassController(ClassService classService){
        this.classService = classService;
    }

    @Autowired
    private RequestService requestservice;

    @Autowired
    private LikesService likeService;


    @ApiOperation(value = "수업 생성")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ClassDto classDto) throws Exception{
        return new ResponseEntity<>(classService.save(classDto), HttpStatus.OK);
    }

    @ApiOperation(value = "개설 요청에 대한 수업 생성")
    @PostMapping("/{rid}")
    public ResponseEntity<?> save(@RequestBody ClassDto classDto,@PathVariable("rid") Long rid) throws Exception{
        Optional<Request> orequest = requestservice.readonly(rid);
        if (orequest.isPresent()) {
            orequest.get().setSolved(true);
            requestservice.update(orequest.get());
        }

        List<Likes> result = likeService.likesList(rid);
        for (int i =0; i<result.size();i++){
            //특정 수업이 개설되면 위수업을 듣고 싶어했던 모든 학생들의 opened를 (false->true)로 바꿔 준다.
            result.get(i).setLOpened(true);
            likeService.updateboolean(result.get(i));
        }
        return new ResponseEntity<>(classService.save(classDto), HttpStatus.OK);
    }

    @ApiOperation(value = "이미지 등록")
    @PostMapping("/image")
    public ResponseEntity<String> fileImage(@RequestParam(name = "thumbnail") MultipartFile multipartFile) throws Exception{
        LocalDateTime now = LocalDateTime.now();
        String dir1 = "src/main/resources/static/";
        String dir2 = "class_thumbnail/";
        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = ""+now.getYear()+now.getMonth()+now.getDayOfMonth()+Long.toString(now.toEpochSecond(ZoneOffset.UTC))+"."+ext;
        try{
            FileUploadUtil.saveFile(dir1+dir2,fileName,multipartFile);
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>("/class/image/"+fileName, HttpStatus.OK);
    }
    @ApiOperation("이미지 받아오기")
    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> showImage(@PathVariable String fileName) throws Exception {
        String dir0 = new File("").getAbsolutePath()+"\\";
        String dir1 = "src\\main\\resources\\static\\class_thumbnail\\";
        return new ResponseEntity<Resource>(new UrlResource("file:"+dir0+dir1+fileName), HttpStatus.OK);
    }
    @ApiOperation(value = "수업 수정")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ClassDto classDto) throws Exception{
        return new ResponseEntity<>(classService.update(classDto), HttpStatus.OK);
    }
    @ApiOperation(value = "수업 리스트 가져오기 (검색가능)")
    @GetMapping
    public ResponseEntity<List<ClassDto>> findAll(@ApiParam("제목 검색") @RequestParam(required = false, name = "title") String title,
                                                  @ApiParam("공개수업 여부")@RequestParam(required = false, name = "opened") Boolean opened,
                                                  @ApiParam("수업 상태 0:진행전, 1:진행중 2:종료") @RequestParam(required = false, name = "state") Integer state,
                                                  @ApiParam("봉사자ID로 검색") @RequestParam(required = false, name = "vid") Long vid) throws Exception{
        ClassDto classDto = new ClassDto();
        classDto.setSearchfQuery(title, opened, state, vid);
        return new ResponseEntity<List<ClassDto>>(classService.findAll(classDto), HttpStatus.OK);
    }
    @ApiOperation(value = "수업 하나 가져오기")
    @GetMapping("/{id}")
    public ResponseEntity<?> findByCId(@PathVariable("id") Long id) throws Exception{
        ClassDto classDto = classService.findByCId(id);
        System.out.println(classDto.toString());
        return new ResponseEntity<>(classDto, HttpStatus.OK);
    }
    @ApiOperation(value = "수업 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws Exception{
        if(classService.deleteById(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
