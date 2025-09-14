package com.servicemarket.repository;

import com.servicemarket.entity.ServiceRequest;
import com.servicemarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
//        List<ServiceRequest> findByCustomerOrderByCreatedAtDesc(User customer);
//
//        List<ServiceRequest> findByStatusOrderByCreatedAtDesc(ServiceRequest.Status status);

        @Query("SELECT sr FROM ServiceRequest sr " +
                "LEFT JOIN FETCH sr.customer " +
                "WHERE sr.customer = :customer " +
                "ORDER BY sr.createdAt DESC")
        List<ServiceRequest> findByCustomerOrderByCreatedAtDesc(@Param("customer") User customer);

        @Query("SELECT sr FROM ServiceRequest sr " +
                "LEFT JOIN FETCH sr.customer " +
                "WHERE sr.status = 'OPEN' AND sr.id NOT IN " +
                "(SELECT q.serviceRequest.id FROM Quote q WHERE q.worker = :worker)")
        List<ServiceRequest> findAvailableRequestsForWorker(@Param("worker") User worker);
//        @Query("SELECT sr FROM ServiceRequest sr WHERE sr.status = 'OPEN' AND sr.id NOT IN " +
//        "(SELECT q.serviceRequest.id FROM Quote q WHERE q.worker = :worker)")
//        List<ServiceRequest> findAvailableRequestsForWorker(@Param("worker") User worker);
//
        @Query("SELECT COUNT(sr) FROM ServiceRequest sr WHERE sr.status = :status")
        long countByStatus(ServiceRequest.Status status);

        @Query("SELECT COUNT(sr) FROM ServiceRequest sr")
        long countAllRequests();
}