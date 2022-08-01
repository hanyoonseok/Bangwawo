package com.ssafy.banggawawo.domain.entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Volunteer {
    @Id
    @GeneratedValue
    @Column(name="v_id")
    private Long v_id;          // 봉사자 아이디

    @OneToMany(mappedBy = "v_id")
    private List<ClassRoom> Class;

    private String v_token;     // 카카오 토큰
    private String v_name;      // 이름
    private int v_birth;        // 출생년도
    private byte v_img;         // 프로필 사진
    private String v_introduce; // 자기소개

    @Embedded
    private Character character;//개인 3d캐릭터에대한 정보
}