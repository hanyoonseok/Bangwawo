package com.ssafy.banggawawo.repository;

import com.ssafy.banggawawo.domain.dto.LikeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeDto,Long> {
}
