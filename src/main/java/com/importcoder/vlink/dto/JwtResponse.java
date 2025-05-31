package com.importcoder.vlink.dto;

import java.util.List;

public class JwtResponse {
    private String token;
    private String username;
    private String email;
    private List<String> roles;
    private String type = "Bearer";

    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public List<String> getRoles() { return roles; }
    public String getType() { return type; }
}
