package com.ssafy.banggawawo.repository;


import com.ssafy.banggawawo.domain.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
}
