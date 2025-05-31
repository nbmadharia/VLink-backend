package com.importcoder.vlink.repository;

import com.importcoder.vlink.entity.SavedQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedQueryRepository extends JpaRepository<SavedQuery, Long> {
    List<SavedQuery> findByUserId(Long userId);
    List<SavedQuery> findByPublishedTrue();
}
