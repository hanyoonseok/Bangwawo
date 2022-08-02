package com.ssafy.banggawawo.controller;

import com.ssafy.banggawawo.domain.dto.RequestDto;
import com.ssafy.banggawawo.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestService requestservice;

    @PostMapping()
    public ResponseEntity<?> write(@RequestBody RequestDto requestdto) throws Exception {

        return 
    }



}
