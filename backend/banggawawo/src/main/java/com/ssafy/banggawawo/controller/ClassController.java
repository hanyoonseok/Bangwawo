package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.service.ClassService;
import com.ssafy.banggawawo.util.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
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
    @ApiOperation(value = "수업 생성")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ClassDto classDto){
        return new ResponseEntity<>(classService.save(classDto), HttpStatus.OK);
    }
    @ApiOperation(value = "이미지 등록")
    @PostMapping("/image")
    public ResponseEntity<String> fileImage(@RequestParam(name = "thumbnail") MultipartFile multipartFile){
        LocalDateTime now = LocalDateTime.now();
        String dir = "src/main/resources/static/class_thumbnail/"+now.getYear()+now.getMonth()+now.getDayOfMonth();
        String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = Long.toString(now.toEpochSecond(ZoneOffset.UTC))+"."+ext;
        try{
            FileUploadUtil.saveFile(dir,fileName,multipartFile);
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }        return new ResponseEntity<>(dir+fileName, HttpStatus.OK);
    }
    @ApiOperation(value = "수업 수정")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ClassDto classDto){
        return new ResponseEntity<>(classService.update(classDto), HttpStatus.OK);
    }
    @ApiOperation(value = "수업 리스트 가져오기")
    @GetMapping
    public ResponseEntity<List<ClassRoom>> findAll(@RequestParam(required = false, name = "title") String title,
                                                   @RequestParam(required = false, name = "opened") Boolean opened,
                                                   @RequestParam(required = false, name = "state") Boolean state){
        ClassDto classDto = new ClassDto();
        classDto.setSearchfQuery(title, opened, state);
        return new ResponseEntity<List<ClassRoom>>(classService.findAll(classDto), HttpStatus.OK);
    }
    @ApiOperation(value = "수업 하나 가져오기")
    @GetMapping("/{id}")
    public ResponseEntity<?> findByCId(@PathVariable("id") Long id){
        ClassDto classDto = classService.findByCId(id);
        System.out.println(classDto.toString());
        return new ResponseEntity<>(classDto, HttpStatus.OK);
    }
    @ApiOperation(value = "수업 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        if(classService.deleteById(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
