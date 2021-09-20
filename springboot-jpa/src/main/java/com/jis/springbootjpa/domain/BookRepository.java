package com.jis.springbootjpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

//Dao와 같이 DB Layer에 접근자
public interface BookRepository extends JpaRepository<Books,Long> {
}
