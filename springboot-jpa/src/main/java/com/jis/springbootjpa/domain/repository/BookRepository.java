package com.jis.springbootjpa.domain.repository;

import com.jis.springbootjpa.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
