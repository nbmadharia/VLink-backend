package com.importcoder.vlink.entity;

import jakarta.persistence.*;

@Entity
public class DatabaseConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dbType; // POSTGRES or MONGO



    private String host;
    private int port;
    private String dbName;
    private String username;
    private String password;
    private String collectionName;

    private String connectionName;

    private Long userId; // foreign key to User

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public Long getId() { return id; }

    public String getDbType() { return dbType; }

    public void setDbType(String dbType) { this.dbType = dbType; }

    public String getHost() { return host; }

    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }

    public void setPort(int port) { this.port = port; }

    public String getDbName() { return dbName; }

    public void setDbName(String dbName) { this.dbName = dbName; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }
    // ✅ Getter
    public String getCollectionName() {
        return collectionName;
    }

    // ✅ Setter
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getUrl() {
        if ("postgresql".equalsIgnoreCase(dbType)) {
            return "jdbc:postgresql://" + host + ":" + port + "/" + dbName;
        }
        // Add more db types as needed
        return null;
    }
}
