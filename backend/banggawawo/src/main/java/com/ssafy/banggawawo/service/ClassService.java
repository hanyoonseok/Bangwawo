package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {


    private final ClassRepository classRepository;
    @Autowired
    public ClassService(ClassRepository classRepository){
        this.classRepository = classRepository;
    }
    private ClassRoom build(ClassDto classDto){
        ClassRoom classRoom = ClassRoom.builder()
                .volunteer(classDto.getVId())    //여기 유저(봉사자) 자리
                .request(classDto.getRId())
                .thumbnail(classDto.getThumbnail())
                .etime(classDto.getEtime())
                .stime(classDto.getStime())
                .introduce(classDto.getIntroduce())
                .maxcnt(classDto.getMaxcnt())
                .opened(classDto.getOpened())
                .title(classDto.getTitle())
                .build();
        return classRoom;
    }
    @Transactional
    public ClassRoom save(ClassDto classDto){
        //서버에 들어온 유저 처리 후 (아니면 DTO에 넣어 온 후)
        ClassRoom classRoom = build(classDto);
        return classRepository.save(classRoom);
    }

    @Transactional
    public ClassRoom update(ClassDto classDto){
        ClassRoom classRoom = build(classDto);
        classRoom.setCId(classDto.getCId());
        classRoom.setState(classDto.getState());
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
    public List<ClassRoom> findAllByOpenedIsFalse(Pageable pageable){
        return classRepository.findAllByOpenedIsFalseOrderByCIdDesc(pageable).getContent();
    }

    @Transactional
    public List<ClassRoom> findAllByOpenedIsTrue(Pageable pageable){
        return classRepository.findAllByOpenedIsTrueOrderByCIdDesc(pageable).getContent();
    }
}
