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
                .classes(enrolDto.getCId())
                .eId(enrolDto.getEId())
                .student(enrolDto.getSId())
                .emotion(enrolDto.getEmotion())
                .feedback(enrolDto.getFeedback())
                .recording(enrolDto.getRecording())
                .build();
        return enrol;
    }
    private EnrolDto trans(Enrol enrol){
        EnrolDto enrolDto = EnrolDto.builder()
                .cId(enrol.getClasses())
                .eId(enrol.getEId())
                .sId(enrol.getStudent())
                .emotion(enrol.getEmotion())
                .feedback(enrol.getFeedback())
                .recording(enrol.getRecording())
                .build();
        return enrolDto;
    }

    @Transactional
    public EnrolDto save(EnrolDto enrolDto){
        Enrol enrol = build(enrolDto);
        enrol = enrolRepository.save(enrol);
        return trans(enrol);
    }


    @Transactional
    public List<Enrol> findByClassRoom(Long cid){;
        List<Enrol> list = enrolRepository.findEnrolsByClasses_cId(cid);
        return list;
    }

    @Transactional
    public List<Enrol> findByStudent(Long sid){
        List<Enrol> list = enrolRepository.findEnrolsByStudent_sId(sid);
        return list;
    }

    @Transactional
    public EnrolDto update(EnrolDto enrolDto){
        Enrol enrol = enrolRepository.findEnrolByClasses_cIdAndStudent_sId(enrolDto.getCId().getCId(), enrolDto.getSId().getSId());
        enrol.setFeedback(enrolDto.getFeedback());
        enrol.setEmotion(enrolDto.getEmotion());
        enrol.setRecording(enrolDto.getRecording());
        return trans(enrolRepository.save(enrol));
    }

    @Transactional
    public EnrolDto findByClassAndStudent(Long cid, Long sid){
        return trans(enrolRepository.findEnrolByClasses_cIdAndStudent_sId(cid,sid));
    }
}
