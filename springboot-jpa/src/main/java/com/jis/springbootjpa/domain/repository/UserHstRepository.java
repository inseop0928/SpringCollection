package com.jis.springbootjpa.domain.repository;

import com.jis.springbootjpa.domain.UserHstEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHstRepository extends JpaRepository<UserHstEntity,Long> {

    List<UserHstEntity> findByUserId(Long userId);
}
