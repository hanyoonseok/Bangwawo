package com.ssafy.banggawawo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecritChat {
    @Id
    @GeneratedValue
    private long sc_id;           //비밀친구 아이디
    private long s_id;            //학생 아이디
    private String sc_content;    //위험 내용
    private Date sc_date;         //날짜
}
