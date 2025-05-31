package com.importcoder.vlink.controller;

import com.importcoder.vlink.service.PublishedQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/query")
public class PublishedQueryController {

    @Autowired
    private PublishedQueryService publishedQueryService;

    @PostMapping("/publish/{queryId}")
    public ResponseEntity<String> publishQuery(@PathVariable Long queryId) {
        return publishedQueryService.publishQuery(queryId)
                ? ResponseEntity.ok("Query published successfully")
                : ResponseEntity.badRequest().body("Failed to publish query");
    }

    @GetMapping("/public/{queryId}")
    public ResponseEntity<Object> executePublicQuery(@PathVariable Long queryId) {
        return ResponseEntity.ok(publishedQueryService.executePublishedQuery(queryId));
    }
}