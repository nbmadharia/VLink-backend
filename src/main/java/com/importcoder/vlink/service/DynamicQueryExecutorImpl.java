package com.importcoder.vlink.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.importcoder.vlink.entity.DatabaseConnection;
import com.importcoder.vlink.entity.enums.DatabaseType;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DynamicQueryExecutorImpl implements DynamicQueryExecutor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Map<String, Object>> execute(DatabaseConnection connection, String query) {
        if (DatabaseType.valueOf(connection.getDbType()) == DatabaseType.POSTGRES) {
            return executePostgresQuery(connection, query);
        } else if (DatabaseType.valueOf(connection.getDbType()) == DatabaseType.MONGO) {
            return executeMongoQuery(connection, query);
        } else {
            throw new UnsupportedOperationException("Unsupported DB type: " + connection.getDbType());
        }
    }

    private List<Map<String, Object>> executePostgresQuery(DatabaseConnection connection, String query) {
        try {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl(connection.getUrl());
            dataSource.setUsername(connection.getUsername());
            dataSource.setPassword(connection.getPassword());

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            return jdbcTemplate.queryForList(query);
        } catch (Exception e) {
            log.error("Postgres Query Execution Error", e);
            throw new RuntimeException("Error executing Postgres query: " + e.getMessage(), e);
        }
    }

    private List<Map<String, Object>> executeMongoQuery(DatabaseConnection connection, String query) {
        try (var mongoClient = MongoClients.create(connection.getUrl())) {
            MongoDatabase database = mongoClient.getDatabase(connection.getDbName());
            MongoCollection<Document> collection = database.getCollection(connection.getCollectionName());

            Document filter = Document.parse(query);

            return collection.find(filter)
                    .into(new ArrayList<>())
                    .stream()
                    .map(doc -> objectMapper.convertValue(doc, new TypeReference<Map<String, Object>>() {}))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Mongo Query Execution Error", e);
            throw new RuntimeException("Error executing Mongo query: " + e.getMessage(), e);
        }
    }
}
