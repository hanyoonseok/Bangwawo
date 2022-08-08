package com.ssafy.banggawawo.repository;

import com.ssafy.banggawawo.domain.dto.ClassDto;
import com.ssafy.banggawawo.domain.entity.ClassRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClassRepository extends JpaRepository<ClassRoom, Long>, JpaSpecificationExecutor<ClassRoom> {
    List<ClassRoom> findClassRoomsByTitleContaining(String title);
}
