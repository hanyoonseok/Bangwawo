package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.repository.ClassRepository;
import com.ssafy.banggawawo.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    @Autowired
    public ClassService(ClassRepository classRepository){
        this.classRepository = classRepository;
    }

    /**
     * @param classDto
     * @return ClassRoom
     */
    private ClassRoom build(ClassDto classDto){
        ClassRoom classRoom = ClassRoom.builder()
                .volunteer(classDto.getVId())    //여기 유저(봉사자) 자리
//                .request(classDto.getRId())
                .thumbnail(classDto.getThumbnail())
                .etime(classDto.getEtime())
                .stime(classDto.getStime())
                .introduce(classDto.getIntroduce())
                .maxcnt(classDto.getMaxcnt())
                .opened(classDto.getOpened())
                .title(classDto.getTitle())
                .cId(classDto.getCId())
                .state(classDto.getState())
                .build();
        return classRoom;
    }
    @Transactional
    public ClassRoom save(ClassDto classDto){
        //클래스 저장
        ClassRoom classRoom = build(classDto);
        classRoom = classRepository.save(classRoom);

        return classRoom;
    }
    @Transactional
    public ClassRoom update(ClassDto classDto){
        ClassRoom classRoom = build(classDto);
        return classRepository.save(classRoom);
    }
    @Transactional(readOnly = true)
    public Optional<ClassRoom> findByCId(Long id){
        return classRepository.findById(id);
    }
    @Transactional
    public void deleteById(Long id){
        classRepository.deleteById(id);
    }
    @Transactional
    public List<ClassRoom> findAll(ClassDto classDto){
        List<ClassRoom> list;
        if(classDto == null){
            return classRepository.findAll();
        }
        //제목검색 모두 가져옴   (없으면 전체 가져옴)
        if(classDto.getTitle() != null){
            list = classRepository.findClassRoomsByTitleContaining(classDto.getTitle());
        }else {
            list = classRepository.findAll();
        }
        //조건에 맞는 것만 남기고 나머지 자르기 (state, opened)
        if(classDto.getState() != null){
            list = list.stream().filter(classRoom -> classRoom.getState() == classDto.getState()).collect(Collectors.toList());
        }
        if(classDto.getOpened() != null){
            list = list.stream().filter(classRoom -> classRoom.getOpened() == classDto.getOpened()).collect(Collectors.toList());
        }
        list = list.stream().sorted((o1, o2) -> (int)(o2.getCId() - o1.getCId())).collect(Collectors.toList());
        return list;
    }
}
