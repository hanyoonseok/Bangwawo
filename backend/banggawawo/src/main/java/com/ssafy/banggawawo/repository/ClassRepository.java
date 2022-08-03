package com.ssafy.banggawawo.repository;

import com.ssafy.banggawawo.domain.entity.ClassRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<ClassRoom, Long> {
    Page<ClassRoom> findAllByOpenedIsFalseOrderByCIdDesc(Pageable pageable);
    Page<ClassRoom> findAllByOpenedIsTrueOrderByCIdDesc(Pageable pageable);
}
