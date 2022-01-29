package com.jis.springbootjpa.domain.repository;


import com.jis.springbootjpa.domain.Member;
import com.jis.springbootjpa.domain.User;
import com.jis.springbootjpa.domain.UserHstEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MemberRepository extends JpaRepository<Member,Long> {


    Optional<Member> findByLoginId(String loginId);
}
