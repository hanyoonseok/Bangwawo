package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.StudentDto;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true) // 조회 시 데이터 변경 방지
    public Optional<Student> findById(Long id){
        return studentRepository.findById(id);
    }

    @Transactional
    public Student save(StudentDto studentDto){
        Student student = Student.builder()
                                    .s_id(studentDto.getS_id())
                                    .s_tonken(studentDto.getS_tonken())
                                    .s_name(studentDto.getS_name())
                                    .s_birth(studentDto.getS_birth())
                                    .s_img(studentDto.getS_img())
                                    .s_nickname(studentDto.getS_nickname())
                                    .s_pemail(studentDto.getS_pemail())
                                    .s_ppw(studentDto.getS_ppw())
                                    .s_character(studentDto.getCharacter()).build();
        return studentRepository.save(student);
    }
}
