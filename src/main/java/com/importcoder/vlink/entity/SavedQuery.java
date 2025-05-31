package com.importcoder.vlink.entity;

import jakarta.persistence.*;

@Entity
public class SavedQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long dbConnectionId;

    @Column(length = 2000)
    private String query;

    private boolean published = false;

    public Long getId() { return id; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getDbConnectionId() { return dbConnectionId; }

    public void setDbConnectionId(Long dbConnectionId) { this.dbConnectionId = dbConnectionId; }

    public String getQuery() { return query; }

    public void setQuery(String query) { this.query = query; }

    public boolean isPublished() { return published; }

    public void setPublished(boolean published) { this.published = published; }
}
