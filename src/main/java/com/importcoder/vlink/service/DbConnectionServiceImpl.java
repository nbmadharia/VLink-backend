package com.importcoder.vlink.service;

import com.importcoder.vlink.entity.DatabaseConnection;
import com.importcoder.vlink.repository.DatabaseConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;

@Service
public class DbConnectionServiceImpl implements DbConnectionService {

    @Autowired
    private DatabaseConnectionRepository dbConnectionRepository;

    @Override
    public DatabaseConnection saveConnection(DatabaseConnection connection) {
        return dbConnectionRepository.save(connection);
    }

    @Override
    public List<DatabaseConnection> getAllConnectionsByUser(Long userId) {
        return dbConnectionRepository.findByUserId(userId);
    }

    @Override
    public List<DatabaseConnection> getAllConnectionsByUsername(String username) {
        return dbConnectionRepository.findByUsername(username);
    }

    @Override
    public DatabaseConnection createConnection(DatabaseConnection dbConnection) {
        return dbConnectionRepository.save(dbConnection);
    }

    @Override
    public List<DatabaseConnection> getConnectionsByUserId(Long userId) {
        return dbConnectionRepository.findByUserId(userId);
    }

    @Override
    public Optional<DatabaseConnection> getConnectionById(Long id) {
        return dbConnectionRepository.findById(id);
    }

    @Override
    public boolean testConnection(DatabaseConnection dbConnection) {
        try {
            String url = dbConnection.getUrl();
            String username = dbConnection.getUsername();
            String password = dbConnection.getPassword();

            Connection conn;
//            System.out.println(dbConnection.getDbType());
            Boolean anm = dbConnection.getDbType().equalsIgnoreCase("postgres");
            if (dbConnection.getDbType().equalsIgnoreCase("postgres")) {
                conn = DriverManager.getConnection(url, username, password);
            } else if (dbConnection.getDbType().equalsIgnoreCase("mongodb")) {
                // Add MongoDB connection logic if needed
                return true; // placeholder
            } else {
                return false;
            }

            boolean isValid = (conn != null && !conn.isClosed());
            conn.close();
            return isValid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void deleteConnection(Long dbConnectionId){
        dbConnectionRepository.deleteById(dbConnectionId);
    }
}
