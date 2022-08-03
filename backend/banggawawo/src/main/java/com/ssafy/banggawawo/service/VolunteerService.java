package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.VolunteerDto;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.domain.entity.Volunteer;
import com.ssafy.banggawawo.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Transactional(readOnly = true) // 조회 시 데이터 변경 방지
    public Optional<Volunteer> findById(Long id){
        return volunteerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Volunteer> findByToken(String token) {
        return volunteerRepository.findByToken(token);
    }

    @Transactional
    public Volunteer save(VolunteerDto volunteerDto) {
        Volunteer volunteer = Volunteer.builder()
                .token(volunteerDto.getToken())
                .name(volunteerDto.getName())
                .birth(volunteerDto.getBirth())
                .introduce(volunteerDto.getIntroduce())
                .character(volunteerDto.getCharacter()).build();
        return volunteerRepository.save(volunteer);
    }

    @Transactional
    public void delete(Volunteer value) {
        volunteerRepository.delete(value);
    }
}
