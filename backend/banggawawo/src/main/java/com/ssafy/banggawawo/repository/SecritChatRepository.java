package com.ssafy.banggawawo.repository;

import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.domain.entity.SecritChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecritChatRepository extends JpaRepository<SecritChat,Long> {
    @Query("SELECT sc FROM SecritChat sc WHERE sc.sId = :sid and sc.parentsCheck=false")
    List<SecritChat> findByIdAndparentsCheck(@Param("sid") Long sid);
}
