package com.servicemarket.repository;

import com.servicemarket.entity.Booking;
import com.servicemarket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
//    List<Booking> findByCustomerOrderByCreatedAtDesc(User customer);
    
//    List<Booking> findByWorkerOrderByCreatedAtDesc(User worker);

    @Query("SELECT b FROM Booking b " +
            "LEFT JOIN FETCH b.serviceRequest " +
            "LEFT JOIN FETCH b.customer " +
            "LEFT JOIN FETCH b.worker " +
            "LEFT JOIN FETCH b.quote " +
            "WHERE b.customer = :customer " +
            "ORDER BY b.createdAt DESC")
    List<Booking> findByCustomerOrderByCreatedAtDesc(@Param("customer") User customer);

    @Query("SELECT b FROM Booking b " +
            "LEFT JOIN FETCH b.serviceRequest " +
            "LEFT JOIN FETCH b.customer " +
            "LEFT JOIN FETCH b.worker " +
            "LEFT JOIN FETCH b.quote " +
            "WHERE b.worker = :worker " +
            "ORDER BY b.createdAt DESC")
    List<Booking> findByWorkerOrderByCreatedAtDesc(@Param("worker") User worker);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status")
    long countByStatus(Booking.Status status);
    
    @Query("SELECT COUNT(b) FROM Booking b")
    long countAllBookings();
    
    @Query("SELECT COALESCE(SUM(b.paymentAmount), 0) FROM Booking b WHERE b.status = 'COMPLETED' AND b.paymentAmount IS NOT NULL")
    BigDecimal calculateTotalRevenue();
}