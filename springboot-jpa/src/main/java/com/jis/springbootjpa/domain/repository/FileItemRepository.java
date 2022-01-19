package com.jis.springbootjpa.domain.repository;

import com.jis.springbootjpa.domain.FileItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileItemRepository extends JpaRepository<FileItem,Long> {
}
