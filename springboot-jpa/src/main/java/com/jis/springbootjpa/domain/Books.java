package com.jis.springbootjpa.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity//테이블과 매핑
@NoArgsConstructor//기본생성자 추가
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255 , nullable = false)//컬럼설정 굳이 테이블인 경우 설정 안 해도 되지만 속성을 설정할때 추가
    private String title;

    @Column(columnDefinition = "TEXT" ,nullable = false)
    private String content;

    private String author;

    @Builder
    public Books(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title,String content){
        this.title = title;
        this.content = content;
    }
}
