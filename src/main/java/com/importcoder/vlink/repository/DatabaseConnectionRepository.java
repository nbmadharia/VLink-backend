package com.importcoder.vlink.repository;

import com.importcoder.vlink.entity.DatabaseConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatabaseConnectionRepository extends JpaRepository<DatabaseConnection, Long> {
    List<DatabaseConnection> findByUserId(Long userId);

    List<DatabaseConnection> findByUsername(String username);
}
