package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.EnrolDto;
import com.ssafy.banggawawo.domain.entity.Enrol;
import com.ssafy.banggawawo.repository.EnrolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnrolService {
    private final EnrolRepository enrolRepository;
    @Autowired
    public EnrolService(EnrolRepository enrolRepository){
        this.enrolRepository = enrolRepository;
    }

    /**
     * @param enrolDto
     * @return Enrol
     */
    private Enrol build(EnrolDto enrolDto){
        Enrol enrol = Enrol.builder()
                .classes(enrolDto.getClassRoom())
                .eId(enrolDto.getEId())
                .student(enrolDto.getStudent())
                .build();
        return enrol;
    }

    @Transactional
    public Enrol save(EnrolDto enrolDto){
        Enrol enrol = build(enrolDto);
        enrol = enrolRepository.save(enrol);
        return enrol;
    }

    @Transactional
    public List<Enrol> findByClassRoom(EnrolDto enrolDto){
        Enrol enrol = build(enrolDto);
        List<Enrol> list = enrolRepository.findEnrolsByStudent_sId(enrol);
        return list;
    }

    @Transactional
    public List<Enrol> findByStudent(EnrolDto enrolDto){
        Enrol enrol = build(enrolDto);
        List<Enrol> list = enrolRepository.findEnrolsByClasses_cId(enrol);
        return list;
    }

    @Transactional
    public Enrol update(EnrolDto enrolDto){
        Enrol enrol = build(enrolDto);
        return enrolRepository.save(enrol);
    }
}
