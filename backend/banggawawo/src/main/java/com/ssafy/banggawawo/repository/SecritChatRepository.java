package com.ssafy.banggawawo.repository;

import com.ssafy.banggawawo.domain.entity.SecritChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecritChatRepository extends JpaRepository<SecritChat,Long> {
}
