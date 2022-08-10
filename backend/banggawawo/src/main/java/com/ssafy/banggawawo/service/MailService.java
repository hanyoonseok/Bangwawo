package com.ssafy.banggawawo.service;

import com.ssafy.banggawawo.domain.dto.MailDto;
import com.ssafy.banggawawo.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    @Autowired
    StudentRepository studentRepository;

    private JavaMailSender mailSender;
    private final String email="yukdabin@naver.com";



    public MailDto createMailAndChangePassword(String userEmail, String userName) {
        String str = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(userEmail);
        dto.setTitle(userName + "님의 [방과워] 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요 :) \n[방과워] 임시비밀번호 안내 관련 이메일 입니다. \n"
                + "[" + userName + "]" + "님의 부모님 계정을 위한 임시 비밀번호는 " + str + " 입니다. " +
                "\n로그인 후 반드시 비밀번호를 수정해주세요 !");
        dto.setTmpPassword(str);
        return dto;
    }

    public String getTempPassword() {
        String str = "";
        for (int i = 0; i < 8; i++) {
            int rndVal = (int) (Math.random() * 62);
            if (rndVal < 10) {
                str += rndVal;
            } else if (rndVal > 35) {
                str += (char) (rndVal + 61);
            } else {
                str += (char) (rndVal + 55);
            }
        }
        return str;
    }

    public void mailSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(email);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
        System.out.println("이메일 전송 완료!");
    }
}
