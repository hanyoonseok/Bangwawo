package com.ssafy.banggawawo.domain.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class MailDto {
    private String address;
    private String title;
    private String message;
    private String tmpPassword;
}
