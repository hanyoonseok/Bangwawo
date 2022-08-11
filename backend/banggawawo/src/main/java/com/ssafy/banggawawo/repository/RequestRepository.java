package com.ssafy.banggawawo.repository;


import com.ssafy.banggawawo.domain.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
    @Query("SELECT r FROM Request r where r.solved =false order by r.rId")
    List<Request> unfindlist();

}
