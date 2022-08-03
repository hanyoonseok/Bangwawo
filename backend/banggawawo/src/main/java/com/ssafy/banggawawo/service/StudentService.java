package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.StudentDto;
import com.ssafy.banggawawo.domain.entity.Student;
import com.ssafy.banggawawo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.OptionalInt;

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
                                    .token(studentDto.getToken())
                                    .name(studentDto.getName())
                                    .birth(studentDto.getBirth())
                                    .nickname(studentDto.getNickname())
                                    .pemail(studentDto.getPemail())
                                    .ppw(studentDto.getPpw())
                                    .character(studentDto.getCharacter()).build();
        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Optional<Student> findByToken(String token) {
        return studentRepository.findByToken(token);
    }

    @Transactional
    public void delete(Student value) {
        studentRepository.delete(value);
    }
}
