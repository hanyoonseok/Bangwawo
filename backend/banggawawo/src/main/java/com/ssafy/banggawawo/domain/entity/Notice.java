//package com.ssafy.banggawawo.domain.entity;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//
//@Getter
//@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Notice {
//    @Id
//    @GeneratedValue
//    private Long n_id;         //알람 아이디
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "l_id")
//    private Like like;         //좋아요 아이디
//
//    private boolean n_read;    //읽기여부
//}
