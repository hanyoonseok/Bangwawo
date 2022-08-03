package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.repository.ClassRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClassService {

    private ClassRepository classRepository;
    @Transactional
    public int createClass(ClassDto classDto){
        //서버에 들어온 유저 처리 후 (아니면 DTO에 넣어 온 후)
        ClassRoom classRoom = ClassRoom.builder()
                .volunteer(null)    //여기 유저(봉사자) 자리
                .request(classDto.getRId())
                .thumbnail(classDto.getThumbnail())
                .etime(classDto.getEtime())
                .stime(classDto.getStime())
                .introduce(classDto.getIntroduce())
                .maxcnt(classDto.getMaxcnt())
                .opened(classDto.getOpened())
                .title(classDto.getTitle())
                .build();

//        classRepository.
        return 0;
    }
}
