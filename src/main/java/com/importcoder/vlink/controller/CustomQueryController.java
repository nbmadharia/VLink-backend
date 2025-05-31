package com.importcoder.vlink.controller;

import com.importcoder.vlink.entity.CustomQuery;
import com.importcoder.vlink.entity.SavedQuery;
import com.importcoder.vlink.service.CustomQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/query")
public class CustomQueryController {

    @Autowired
    private CustomQueryService customQueryService;

    @PostMapping("/save")
    public ResponseEntity<CustomQuery> saveQuery(@RequestParam Long userId,
                                                 @RequestParam Long dbConnectionId,
                                                 @RequestBody String queryText) {
        return ResponseEntity.ok(customQueryService.createCustomQuery(userId, dbConnectionId, queryText));
    }


    @GetMapping("/getUserQuery/{userId}")
    public ResponseEntity<List<CustomQuery>> getAllQueries(@PathVariable Long userId) {
        return ResponseEntity.ok(customQueryService.getCustomQueriesByUser(userId));
    }

    @GetMapping("/runQuery/{queryId}")
    public ResponseEntity<String> runQuery(@PathVariable Long queryId) {
        return ResponseEntity.ok(customQueryService.runCustomQuery(queryId));
    }
}