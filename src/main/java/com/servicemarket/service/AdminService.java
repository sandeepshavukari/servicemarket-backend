package com.servicemarket.service;

import com.servicemarket.dto.PlatformStatsDto;
import com.servicemarket.entity.Booking;
import com.servicemarket.entity.ServiceRequest;
import com.servicemarket.entity.User;
import com.servicemarket.repository.BookingRepository;
import com.servicemarket.repository.ServiceRequestRepository;
import com.servicemarket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public PlatformStatsDto getPlatformStats() {
        PlatformStatsDto stats = new PlatformStatsDto();

        // User statistics
        stats.setTotalUsers(userRepository.countAllUsers());
        stats.setCustomers(userRepository.countByRole(User.Role.CUSTOMER));
        stats.setWorkers(userRepository.countByRole(User.Role.WORKER));
        stats.setAdmins(userRepository.countByRole(User.Role.ADMIN));

        // Request statistics
        stats.setTotalRequests(serviceRequestRepository.countAllRequests());
        stats.setOpenRequests(serviceRequestRepository.countByStatus(ServiceRequest.Status.OPEN));
        stats.setBookedRequests(serviceRequestRepository.countByStatus(ServiceRequest.Status.BOOKED));

        // Booking statistics
        stats.setTotalBookings(bookingRepository.countAllBookings());
        stats.setCompletedBookings(bookingRepository.countByStatus(Booking.Status.COMPLETED));

        // Revenue statistics
        BigDecimal totalRevenue = bookingRepository.calculateTotalRevenue();
        stats.setTotalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO);

        return stats;
    }
}