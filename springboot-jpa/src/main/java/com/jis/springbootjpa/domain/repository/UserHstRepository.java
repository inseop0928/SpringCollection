package com.jis.springbootjpa.domain.repository;

import com.jis.springbootjpa.domain.UserHstEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHstRepository extends JpaRepository<UserHstEntity,Long> {
}
