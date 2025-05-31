package com.importcoder.vlink.service;

import com.importcoder.vlink.entity.DatabaseConnection;

import java.util.List;
import java.util.Map;

public interface DynamicQueryExecutor {
    List<Map<String, Object>> execute(DatabaseConnection connection, String query);
}
