package com.servicemarket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_requests")
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "authorities",
            "accountNonExpired", "accountNonLocked", "credentialsNonExpired",
            "enabled", "username"})
    private User customer;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    private String title;

    @Column(nullable = false, length = 1000)
    @NotBlank(message = "Description is required")
    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Category is required")
    private String category;

    @Column(nullable = false)
    @NotBlank(message = "Urgency is required")
    private String urgency;

    @Column(nullable = false)
    @NotBlank(message = "Location is required")
    private String location;

    @Column(name = "photo_url")
    private String photoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.OPEN;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "serviceRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnoreProperties({"serviceRequest"})
    @JsonIgnore
    private List<Quote> quotes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public ServiceRequest() {}

    public ServiceRequest(User customer, String title, String description, String category, 
                         String urgency, String location, String photoUrl) {
        this.customer = customer;
        this.title = title;
        this.description = description;
        this.category = category;
        this.urgency = urgency;
        this.location = location;
        this.photoUrl = photoUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    // Helper method to get customer ID for JSON serialization
    public Long getCustomerId() {
        return customer != null ? customer.getId() : null;
    }

    public enum Status {
        OPEN, BOOKED, COMPLETED
    }
}