package com.jis.springbootjpa.domain.repository;

import com.jis.springbootjpa.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {
}
