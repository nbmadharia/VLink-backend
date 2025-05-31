package com.importcoder.vlink.service;

import com.importcoder.vlink.entity.CustomQuery;
import java.util.List;

public interface CustomQueryService {
    CustomQuery createCustomQuery(Long userId, Long dbConnectionId, String queryText);
    List<CustomQuery> getCustomQueriesByUser(Long userId);
    String runCustomQuery(Long customQueryId);

}
