package com.servicemarket.dto;

import com.servicemarket.entity.User;

public class AuthResponse {
    private String token;
    private UserDto user;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = new UserDto(user);
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    // Inner class for user data
    public static class UserDto {
        private String id;
        private String email;
        private String name;
        private String role;
        private String createdAt;

        public UserDto() {}

        public UserDto(User user) {
            this.id = user.getId().toString();
            this.email = user.getEmail();
            this.name = user.getName();
            this.role = user.getRole().name().toLowerCase();
            this.createdAt = user.getCreatedAt().toString();
        }

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}