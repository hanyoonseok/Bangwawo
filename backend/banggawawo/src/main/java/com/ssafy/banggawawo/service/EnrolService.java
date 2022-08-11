package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.dto.EnrolDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import com.ssafy.banggawawo.domain.entity.Enrol;
import com.ssafy.banggawawo.repository.ClassRepository;
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

    @Autowired
    private ClassRepository classRepository;

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
    public EnrolDto save(EnrolDto enrolDto) throws Exception{
        Enrol enrol = build(enrolDto);
        //제한인원 확인
        Long cid = enrolDto.getCId().getCId();
        ClassRoom classRoom = classRepository.findById(cid).get();
        if(classRoom != null) {
            int maxCnt = classRoom.getMaxcnt();
            int size = enrolRepository.findEnrolsByClasses_cId(cid).size();
            if(maxCnt <= size) return null;
        }
        enrol = enrolRepository.save(enrol);
        return trans(enrol);
    }


    @Transactional
    public List<Enrol> findByClassRoom(Long cid) throws Exception {
        List<Enrol> list = enrolRepository.findEnrolsByClasses_cId(cid);
        return list;
    }

    @Transactional
    public List<Enrol> findByStudent(Long sid) throws Exception{
        List<Enrol> list = enrolRepository.findEnrolsByStudent_sId(sid);
        return list;
    }

    @Transactional
    public EnrolDto update(EnrolDto enrolDto) throws Exception{
        Enrol enrol = enrolRepository.findEnrolByClasses_cIdAndStudent_sId(enrolDto.getCId().getCId(), enrolDto.getSId().getSId());
        enrol.setFeedback(enrolDto.getFeedback());
        enrol.setEmotion(enrolDto.getEmotion());
        enrol.setRecording(enrolDto.getRecording());
        return trans(enrolRepository.save(enrol));
    }

    @Transactional
    public EnrolDto findByClassAndStudent(Long cid, Long sid) throws Exception {
        return trans(enrolRepository.findEnrolByClasses_cIdAndStudent_sId(cid,sid));
    }

    @Transactional
    public boolean deleteByClassId(Long cid) throws Exception {
        try{
            enrolRepository.deleteAllByClasses_cId(cid);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Transactional
    public Long countEnrolsByClassId(Long cid) throws Exception {
        Long tmp = enrolRepository.countEnrolsByClasses_cId(cid);
        return tmp;
    }
}
