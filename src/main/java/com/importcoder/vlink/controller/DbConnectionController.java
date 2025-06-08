package com.importcoder.vlink.controller;


import org.springframework.http.HttpStatus;


import com.importcoder.vlink.entity.DatabaseConnection;
import com.importcoder.vlink.entity.User;
import com.importcoder.vlink.repository.UserRepository;
import com.importcoder.vlink.service.DbConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.security.Principal;



import java.util.List;

@RestController
@RequestMapping("/api/connection")
public class DbConnectionController {

    @Autowired
    private DbConnectionService dbConnectionService;

    @Autowired
    private UserRepository userRepository;
    @PostMapping("/add")
    public ResponseEntity<DatabaseConnection> addConnection(@RequestBody DatabaseConnection connection, Principal principal) {

        String username= principal.getName();

        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found or not logged in")
        ));
        connection.setUserId(user.get().getId());

        return ResponseEntity.ok(dbConnectionService.saveConnection(connection));
    }

    @GetMapping("/getAll/{username}")
    public ResponseEntity<List<DatabaseConnection>> getAllConnections(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long userId = user.getId();
        return ResponseEntity.ok(dbConnectionService.getConnectionsByUserId(userId));
    }


    @PostMapping("/test/{username}/{connectionId}")
    public ResponseEntity<String> testConnection(@PathVariable String username, @PathVariable Long connectionId) {

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Long userId = userOpt.get().getId();

        Optional<DatabaseConnection> connectionOpt = dbConnectionService.getConnectionById(connectionId);
        if (connectionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Connection not found");
        }

        DatabaseConnection connection = connectionOpt.get();

        // Ensure the connection belongs to the user
        if (!connection.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body("Unauthorized access to this connection");
        }

        boolean isSuccess = dbConnectionService.testConnection(connection);
        return isSuccess
                ? ResponseEntity.ok("Connection successful")
                : ResponseEntity.badRequest().body("Connection failed");
    }

    @PostMapping("/delete/{username}/{connectionId}")
    public ResponseEntity<String> deleteConnection(@PathVariable String username, @PathVariable Long connectionId){
        dbConnectionService.deleteConnection(connectionId);
        return ResponseEntity.ok("Deleted successful");

    }


}