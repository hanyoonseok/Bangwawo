package com.ssafy.banggawawo.repository;

import com.ssafy.banggawawo.domain.entity.Likes;
import com.ssafy.banggawawo.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Query("SELECT l FROM Likes l WHERE l.rId = :rid and l.sId= :sid")
    List<Likes> findByRIdAndStudentSId(@Param("rid") Long rid, @Param("sid") Long sid);

    @Query("SELECT l FROM Likes l WHERE l.rId = :rid")
    List<Likes> findByRId(@Param("rid") Long rid);

    @Query("SELECT l FROM Likes l WHERE l.sId= :sid and l.lOpened= true and l.lRead=false")
    List<Likes> findBySIdAndLOpenedAAndLRead(@Param("sid") Long sid);

}
