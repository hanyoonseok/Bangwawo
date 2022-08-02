package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    @PostMapping("create")
    public ResponseEntity<?> createClass(@RequestBody ClassDto classDto){

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
