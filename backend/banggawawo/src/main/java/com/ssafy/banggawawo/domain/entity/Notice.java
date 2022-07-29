package com.ssafy.banggawawo.domain.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Getter
public class Notice {
    @Id
    @GeneratedValue
    private long n_id;         //알람 아이디
    private long l_id;         //좋아요 아이디
    private boolean n_read;    //읽기여부
}
