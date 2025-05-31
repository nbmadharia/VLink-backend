package com.importcoder.vlink.service;

import com.importcoder.vlink.entity.CustomQuery;
import com.importcoder.vlink.entity.DatabaseConnection;
import com.importcoder.vlink.entity.PublishedQuery;
import com.importcoder.vlink.repository.CustomQueryRepository;
import com.importcoder.vlink.repository.DatabaseConnectionRepository;
import com.importcoder.vlink.repository.PublishedQueryRepository;
import com.importcoder.vlink.service.DynamicQueryExecutor;
import com.importcoder.vlink.service.PublishedQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublishedQueryServiceImpl implements PublishedQueryService {

    @Autowired
    private DatabaseConnectionRepository dbConnectionRepository;


    private final PublishedQueryRepository publishedQueryRepository;
    private final CustomQueryRepository customQueryRepository;
    private final DynamicQueryExecutor dynamicQueryExecutor;

    @Override
    public boolean publishQuery(Long queryId) {
        Optional<CustomQuery> customQueryOpt = customQueryRepository.findById(queryId);
        if (customQueryOpt.isEmpty()) {
            return false;
        }

        CustomQuery customQuery = customQueryOpt.get();

        PublishedQuery publishedQuery = new PublishedQuery();
        publishedQuery.setCustomQuery(customQuery);
        publishedQuery.setName("Published-" + customQuery.getId());

        publishedQueryRepository.save(publishedQuery);
        return true;
    }

    @Override
    public Object executePublishedQuery(Long queryId) {
        Optional<PublishedQuery> publishedQueryOpt = publishedQueryRepository.findById(queryId);
        if (publishedQueryOpt.isEmpty()) {
            return "Query not found";
        }


        CustomQuery customQuery = publishedQueryOpt.get().getCustomQuery();

        DatabaseConnection dbConn = dbConnectionRepository.findById(customQuery.getDbConnectionId())
                .orElseThrow(() -> new RuntimeException("Database connection not found"));


        return dynamicQueryExecutor.execute(dbConn, customQuery.getQueryText()).toString();

    }
}
