package com.jis.springbootjpa.domain.repository;

import com.jis.springbootjpa.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
