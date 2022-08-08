package com.ssafy.banggawawo.repository;

import com.ssafy.banggawawo.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByKakaoId(String kakaoId);
}
