package com.importcoder.vlink.service;

import com.importcoder.vlink.entity.CustomQuery;
import com.importcoder.vlink.entity.DatabaseConnection;
import com.importcoder.vlink.repository.CustomQueryRepository;
import com.importcoder.vlink.repository.DatabaseConnectionRepository;
import com.importcoder.vlink.service.CustomQueryService;
import com.importcoder.vlink.service.DynamicQueryExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomQueryServiceImpl implements CustomQueryService {

    @Autowired
    private CustomQueryRepository customQueryRepository;

    @Autowired
    private DatabaseConnectionRepository dbConnectionRepository;

    @Autowired
    private DynamicQueryExecutor dynamicQueryExecutor;

    @Override
    public CustomQuery createCustomQuery(Long userId, Long dbConnectionId, String queryText) {
        CustomQuery query = new CustomQuery();
        query.setUserId(userId);
        query.setDbConnectionId(dbConnectionId);
        query.setQueryText(queryText);
        return customQueryRepository.save(query);
    }

    @Override
    public List<CustomQuery> getCustomQueriesByUser(Long userId) {
        return customQueryRepository.findByUserId(userId);
    }

    @Override
    public String runCustomQuery(Long customQueryId) {
        Optional<CustomQuery> optionalQuery = customQueryRepository.findById(customQueryId);
        if (optionalQuery.isEmpty()) {
            throw new RuntimeException("Custom query not found");
        }

        CustomQuery customQuery = optionalQuery.get();
        DatabaseConnection dbConn = dbConnectionRepository.findById(customQuery.getDbConnectionId())
                .orElseThrow(() -> new RuntimeException("Database connection not found"));

        return dynamicQueryExecutor.execute(dbConn, customQuery.getQueryText()).toString();
    }
}
