package com.ssafy.banggawawo.repository;

import com.ssafy.banggawawo.domain.entity.Enrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrolRepository  extends JpaRepository<Enrol, Long>, JpaSpecificationExecutor<Enrol> {
    List<Enrol> findEnrolsByStudent_sId(Long sid);
    List<Enrol> findEnrolsByClasses_cId(Long cid);
    Enrol findEnrolByClasses_cIdAndStudent_sId(Long cid, Long sid);
}
