package com.ssafy.banggawawo.repository;

import com.ssafy.banggawawo.domain.entity.Enrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnrolRepository  extends JpaRepository<Enrol, Long>, JpaSpecificationExecutor<Enrol> {
    List<Enrol> findEnrolsByStudent_sId(Enrol enrol);
    List<Enrol> findEnrolsByClasses_cId(Enrol enrol);
}
