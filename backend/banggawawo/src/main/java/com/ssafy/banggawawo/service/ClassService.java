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
    private ClassRoom build(ClassDto classDto) {
        ClassRoom classRoom = ClassRoom.builder()
                .cId(classDto.getCId())
                .volunteer(classDto.getVId())    //여기 유저(봉사자) 자리
                .rId(classDto.getRId())
                .thumbnail(classDto.getThumbnail())
                .etime(classDto.getEtime())
                .stime(classDto.getStime())
                .introduce(classDto.getIntroduce())
                .maxcnt(classDto.getMaxcnt())
                .opened(classDto.getOpened())
                .title(classDto.getTitle())
                .state(classDto.getState())
                .build();
        return classRoom;
    }
    private ClassDto trans(ClassRoom classRoom){
        ClassDto classDto = ClassDto.builder()
                .cId(classRoom.getCId())
                .vId(classRoom.getVolunteer())
                .rId(classRoom.getRId())
                .thumbnail(classRoom.getThumbnail())
                .etime(classRoom.getEtime())
                .stime(classRoom.getStime())
                .introduce(classRoom.getIntroduce())
                .maxcnt(classRoom.getMaxcnt())
                .opened(classRoom.getOpened())
                .title(classRoom.getTitle())
                .state(classRoom.getState())
                .build();
        return classDto;
    }
    @Transactional
    public ClassDto save(ClassDto classDto) throws Exception{
        //클래스 저장
        ClassRoom classRoom = build(classDto);
        classRoom = classRepository.save(classRoom);
        return trans(classRoom);
    }
    @Transactional
    public ClassDto update(ClassDto classDto) throws Exception{
        classRepository.findById(classDto.getCId()).get();
        ClassRoom classRoom = build(classDto);
        classRoom = classRepository.save(classRoom);
        return trans(classRoom);
    }
    @Transactional
    public ClassDto findByCId(Long id) throws Exception{
        ClassDto classDto = null;
        Optional<ClassRoom> classRoom = classRepository.findById(id);
        if(classRoom.isPresent()){
            classDto = trans(classRoom.get());
        }
        return classDto;
    }

    @Transactional
    public boolean deleteById(Long id) throws Exception{
        try{
            classRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    @Transactional
    public List<ClassDto> findAll(ClassDto classDto) throws Exception{
        List<ClassRoom> list;
        if(classDto == null){
            return classRepository.findAll().stream().map(classRoom -> trans(classRoom)).collect(Collectors.toList());
        }
        //제목검색 모두 가져옴   (없으면 전체 가져옴)
        if(classDto.getTitle() != null){
            list = classRepository.findClassRoomsByTitleContaining(classDto.getTitle());
        }else {
            list = classRepository.findAll();
        }

        //vid 있으면 해당 vid 가진 수업만 남기기
        if(classDto.getVId().getVId() != null){
            list = list.stream().filter(classRoom -> classRoom.getVolunteer().getVId() == classDto.getVId().getVId()).collect(Collectors.toList());
        }

        //조건에 맞는 것만 남기고 나머지 자르기 (state, opened)
        if(classDto.getState() != null){
            list = list.stream().filter(classRoom -> classRoom.getState() == classDto.getState()).collect(Collectors.toList());
        }
        if(classDto.getOpened() != null){
            list = list.stream().filter(classRoom -> classRoom.getOpened() == classDto.getOpened()).collect(Collectors.toList());
        }

        //정렬
        list = list.stream().sorted((o1, o2) -> (int)(o2.getCId() - o1.getCId())).collect(Collectors.toList());
        //time 바꿔주기
        List<ClassDto> list2 = list.stream().map(classRoom -> trans(classRoom)).collect(Collectors.toList());

        return list2;
    }
}
