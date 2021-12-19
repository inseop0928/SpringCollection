package com.jis.springbootjpa.domain.listener;

import com.jis.springbootjpa.common.support.BeanUtils;
import com.jis.springbootjpa.domain.User;
import com.jis.springbootjpa.domain.UserHstEntity;
import com.jis.springbootjpa.domain.repository.UserHstRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Auditable;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserEntityListener {

    //entityListner는 bean을 생성하고 주입받지 못하기 때문에 이용

    @PrePersist
    public void prePersist(Object obj){
        if(obj instanceof User){
            System.out.println("prepersist1");
            ((User) obj).setCreatedAt(LocalDateTime.now());
        }
    }

    @PostPersist
    public void postPersist(Object obj){
        UserHstRepository userHstRepository = BeanUtils.getBean(UserHstRepository.class);

        if(obj instanceof User){
            User user = (User)obj;
            UserHstEntity userHstEntity = new UserHstEntity();
            userHstEntity.setId(user.getId());
            userHstEntity.setName(user.getName());
            userHstEntity.setEmail(user.getEmail());
            userHstRepository.save(userHstEntity);
        }
    }


}
