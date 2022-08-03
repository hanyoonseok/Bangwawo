package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import com.ssafy.banggawawo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {
    private ClassService classService;

    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody ClassDto classDto){
        int result = classService.createClass(classDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> updateClass(@RequestBody ClassDto classDto){

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClassRoom>> selectListClass(@RequestBody ClassDto classDto){

        return new ResponseEntity<List<ClassRoom>>((List<ClassRoom>) null, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClassRoom> selectClass(@PathVariable("id") Long id){

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClass(@PathVariable("id") Long id){

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
