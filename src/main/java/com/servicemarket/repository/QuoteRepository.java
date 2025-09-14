package com.servicemarket.repository;

import com.servicemarket.entity.Quote;
import com.servicemarket.entity.ServiceRequest;
import com.servicemarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByWorkerOrderByCreatedAtDesc(User worker);

    @Query("SELECT q FROM Quote q " +
            "LEFT JOIN FETCH q.worker " +
            "LEFT JOIN FETCH q.serviceRequest " +
            "WHERE q.serviceRequest.id = :requestId " +
            "ORDER BY q.createdAt ASC")
    List<Quote> findByServiceRequestIdOrderByCreatedAtAsc(@Param("requestId") Long requestId);
    
    List<Quote> findByServiceRequestOrderByCreatedAtAsc(ServiceRequest serviceRequest);
    
    Optional<Quote> findByWorkerAndServiceRequest(User worker, ServiceRequest serviceRequest);
    
    boolean existsByWorkerAndServiceRequest(User worker, ServiceRequest serviceRequest);
}