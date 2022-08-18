package com.ssafy.banggawawo.domain.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterColor {
    private String part;
    private String color;
}
