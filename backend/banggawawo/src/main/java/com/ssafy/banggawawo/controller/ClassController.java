package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.service.ClassService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "수업 수정")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody ClassDto classDto){
        return new ResponseEntity<>(classService.update(classDto), HttpStatus.OK);
    }
    @ApiOperation(value = "비공개 수업 리스트 가져오기(최근 만든 순)")
    @GetMapping
    public ResponseEntity<List<ClassRoom>> findByOpenedFalse(Pageable pageable){
        return new ResponseEntity<List<ClassRoom>>(classService.findAllByOpenedIsFalse(pageable), HttpStatus.OK);
    }
    @ApiOperation(value = "공개 수업 리스트 가져오기(최근 만든 순)")
    @GetMapping("/opened")
    public ResponseEntity<List<ClassRoom>> findByOpenedTrue(Pageable pageable){
        return new ResponseEntity<List<ClassRoom>>(classService.findAllByOpenedIsTrue(pageable), HttpStatus.OK);
    }
    @ApiOperation(value = "수업 하나 가져오기")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ClassRoom>> findByCId(@PathVariable("id") Long id){
        return new ResponseEntity<>(classService.findByCId(id), HttpStatus.OK);
    }
    @ApiOperation(value = "수업 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
