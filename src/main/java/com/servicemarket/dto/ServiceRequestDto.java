package com.servicemarket.dto;

import jakarta.validation.constraints.NotBlank;

public class ServiceRequestDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Urgency is required")
    private String urgency;

    @NotBlank(message = "Location is required")
    private String location;

    private String photoUrl;

    // Constructors
    public ServiceRequestDto() {}

    public ServiceRequestDto(String title, String description, String category, 
                           String urgency, String location, String photoUrl) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.urgency = urgency;
        this.location = location;
        this.photoUrl = photoUrl;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}