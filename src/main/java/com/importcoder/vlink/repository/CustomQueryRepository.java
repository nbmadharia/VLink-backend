package com.importcoder.vlink.repository;

import com.importcoder.vlink.entity.CustomQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomQueryRepository extends JpaRepository<CustomQuery, Long> {
    List<CustomQuery> findByUserId(Long userId);
}
