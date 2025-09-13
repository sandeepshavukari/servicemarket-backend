package com.servicemarket.repository;

import com.servicemarket.entity.Quote;
import com.servicemarket.entity.ServiceRequest;
import com.servicemarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByWorkerOrderByCreatedAtDesc(User worker);
    
    List<Quote> findByServiceRequestOrderByCreatedAtAsc(ServiceRequest serviceRequest);
    
    Optional<Quote> findByWorkerAndServiceRequest(User worker, ServiceRequest serviceRequest);
    
    boolean existsByWorkerAndServiceRequest(User worker, ServiceRequest serviceRequest);
}