package com.importcoder.vlink.dto;

public class QueryRequest {
    private Long dbConnectionId;
    private String query;

    public Long getDbConnectionId() { return dbConnectionId; }
    public void setDbConnectionId(Long dbConnectionId) { this.dbConnectionId = dbConnectionId; }
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
}
