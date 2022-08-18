package com.ssafy.banggawawo.domain.dto;

import com.ssafy.banggawawo.domain.entity.Character;
import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorDto {
    private String part;
    private String color;
}
