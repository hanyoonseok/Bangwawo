package com.ssafy.banggawawo.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ParentDto {
    private String email;
    private String password;

    public ParentDto(String pemail, String ppw) {
        this.email = pemail;
        this.password = ppw;
    }
}