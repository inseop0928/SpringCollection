package com.jis.springbootjpa.domain.repository;

import com.jis.springbootjpa.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;

//Dao와 같이 DB Layer에 접근자
public interface BooksRepository extends JpaRepository<Books, Long> {
}
