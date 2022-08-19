package com.ssafy.banggawawo.repository;

import com.ssafy.banggawawo.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByKakaoId(String kakaoId);

    List<Student> findByPemail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Student s SET s.ppw = ?2 WHERE s.pemail = ?1")
    int updatePpw(String email, String password);
}
