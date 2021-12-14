package com.jis.springbootjpa.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;


//필드만 원하는경우 필드에 getter setter 설정

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(value = UserEntityListener.class)
@Table(name = "user", indexes = {@Index(columnList = "name")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @Enumerated(value = EnumType.STRING)//String으로 선언하지 않으면 index로 리턴하는데 순서 변경 시 값이 꼬일 수가 있다.
    private Gender gender;

    @Column(nullable = true, insertable = true)
    private LocalDateTime createdAt;

    @Column(insertable = false)
    private LocalDateTime updatedAt;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    //조회 후 동작 preload는 없음
    @PostLoad
    public void postLoad(){
        log.info("postLoad");
    }

    //insert전에 동작
//    @PrePersist
//    public void prePersist(){
//        System.out.println("prePersist");
//    }

    //insert후에 동작
    @PostPersist
    public void postPersist(){
        log.info("postPersist");
    }

    //update 전에 동작
    @PreUpdate
    public void preUpdate(){
        log.info("preUpdate");
    }

    //update 후에 동작
    @PostUpdate
    public void postUpdate(){
        log.info("postUpdate");
    }

    //삭제전 에 동작
    @PreRemove
    public void preRemove(){
        log.info("preRemove");
    }

    //삭제 후에 동작
    @PostRemove
    public void postRemove(){
        log.info("postRemove");
    }
}
