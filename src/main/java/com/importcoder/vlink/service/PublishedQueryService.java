package com.importcoder.vlink.service;

public interface PublishedQueryService {
    boolean publishQuery(Long queryId);
    Object executePublishedQuery(Long queryId);
}
