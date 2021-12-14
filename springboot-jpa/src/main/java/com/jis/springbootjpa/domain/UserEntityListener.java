package com.jis.springbootjpa.domain;

import com.jis.springbootjpa.domain.repository.UserHstRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Auditable;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserEntityListener {

    @Autowired
    private UserHstRepository userHstRepository;

    @PrePersist
    public void prePersist(Object obj){
        if(obj instanceof User){
            System.out.println("prepersist1");
            ((User) obj).setCreatedAt(LocalDateTime.now());
        }
    }

    @PostPersist
    public void postPersist(Object o){

    }


}
