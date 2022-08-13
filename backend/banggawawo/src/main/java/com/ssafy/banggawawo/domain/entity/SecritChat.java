package com.ssafy.banggawawo.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecritChat {
    @Id
    @GeneratedValue
    @Column(name = "sc_id")
    private Long scid;            //비밀친구 아이디
    private Long sId;
    private String sName;
    @Column(name = "sc_content")
    private String sccontent;     //위험 내용
    private Boolean parentsCheck; // 부모님 알람 확인

    @Builder
    public SecritChat(Long sId, String sccontent,String sName,boolean parentsCheck) {
        this.sName=sName;
        this.sId = sId;
        this.sccontent = sccontent;
        this.parentsCheck = parentsCheck;
    }
}
