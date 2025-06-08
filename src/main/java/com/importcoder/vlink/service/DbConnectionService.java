package com.importcoder.vlink.service;

import com.importcoder.vlink.entity.DatabaseConnection;

import java.util.List;
import java.util.Optional;

public interface DbConnectionService {
    DatabaseConnection createConnection(DatabaseConnection DatabaseConnection);

    DatabaseConnection saveConnection(DatabaseConnection connection);

    List<DatabaseConnection> getConnectionsByUserId(Long userId);

    Optional<DatabaseConnection> getConnectionById(Long id);

    List<DatabaseConnection> getAllConnectionsByUser(Long userId);

    List<DatabaseConnection> getAllConnectionsByUsername(String username);


    boolean testConnection(DatabaseConnection DatabaseConnection);

    void deleteConnection(Long dbConnectionId);
}
