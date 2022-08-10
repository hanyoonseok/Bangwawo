package com.ssafy.banggawawo.domain.entity;

import lombok.*;

import javax.persistence.*;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {
    private String body;     //3d 캐릭터 몸 색상
    private String foot;     //3d 캐릭터 다리 색상
    private String glasses;  //3d 캐릭터 안경 색상
    private String clothes;    //3d 캐릭터 드레스 색상
    private String bag;      //3d 캐릭터 방 색상
    private String hat;    //3d 캐릭터 모자 색상
}





