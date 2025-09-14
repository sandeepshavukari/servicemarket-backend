package com.servicemarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quotes")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "authorities",
            "accountNonExpired", "accountNonLocked", "credentialsNonExpired",
            "enabled", "username"})
    private User worker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_request_id", nullable = false)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "quotes"})
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "quotes", "customer"})
    private ServiceRequest serviceRequest;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "Description is required")
    private String description;

    @Column(name = "estimated_duration", nullable = false)
    @NotBlank(message = "Estimated duration is required")
    private String estimatedDuration;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public Quote() {}

    public Quote(User worker, ServiceRequest serviceRequest, BigDecimal price, 
                String description, String estimatedDuration) {
        this.worker = worker;
        this.serviceRequest = serviceRequest;
        this.price = price;
        this.description = description;
        this.estimatedDuration = estimatedDuration;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(String estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Helper methods for JSON serialization
    public Long getWorkerId() {
        return worker != null ? worker.getId() : null;
    }

    public String getWorkerName() {
        return worker != null ? worker.getName() : null;
    }

    public Long getRequestId() {
        return serviceRequest != null ? serviceRequest.getId() : null;
    }
}