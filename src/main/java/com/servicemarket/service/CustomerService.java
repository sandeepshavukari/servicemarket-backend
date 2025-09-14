package com.servicemarket.service;

import com.servicemarket.dto.BookingDto;
import com.servicemarket.dto.CompletionDto;
import com.servicemarket.dto.ServiceRequestDto;
import com.servicemarket.entity.*;
import com.servicemarket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ServiceRequest> getCustomerRequests(User customer) {
        return serviceRequestRepository.findByCustomerOrderByCreatedAtDesc(customer);
    }

    public ServiceRequest createServiceRequest(ServiceRequestDto dto, User customer) {
        ServiceRequest request = new ServiceRequest();
        request.setCustomer(customer);
        request.setTitle(dto.getTitle());
        request.setDescription(dto.getDescription());
        request.setCategory(dto.getCategory());
        request.setUrgency(dto.getUrgency());
        request.setLocation(dto.getLocation());
        request.setPhotoUrl(dto.getPhotoUrl());
        
        return serviceRequestRepository.save(request);
    }

    public List<Booking> getCustomerBookings(User customer) {
        return bookingRepository.findByCustomerOrderByCreatedAtDesc(customer);
    }
    public ServiceRequest getRequestById(Long requestId) {
        return serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Service request not found with id: " + requestId));
    }

    public Booking createBooking(BookingDto dto, User customer) {
        // Find the service request
        ServiceRequest serviceRequest = serviceRequestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Service request not found"));

        // Verify that the customer owns this request
        if (!serviceRequest.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException("Unauthorized access to service request");
        }

        // Find the quote
        Quote quote = quoteRepository.findById(dto.getQuoteId())
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        // Verify that the quote belongs to this service request
        if (!quote.getServiceRequest().getId().equals(serviceRequest.getId())) {
            throw new RuntimeException("Quote does not belong to this service request");
        }

        // Create booking
        User worker = quote.getWorker();
        Booking booking = new Booking(serviceRequest, customer, worker, quote);
        
        // Update service request status
        serviceRequest.setStatus(ServiceRequest.Status.BOOKED);
        serviceRequestRepository.save(serviceRequest);

        return bookingRepository.save(booking);
    }
    public Booking completeBooking(Long bookingId, CompletionDto dto, User customer) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Verify that the customer owns this booking
        if (!booking.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException("Unauthorized access to booking");
        }

        // Update booking with completion details
        booking.setStatus(Booking.Status.COMPLETED);
        booking.setPaymentAmount(dto.getPaymentAmount());
        booking.setFeedback(dto.getFeedback());
        booking.setRating(dto.getRating());
        booking.setCompletedAt(LocalDateTime.now());

        // Update service request status
        ServiceRequest serviceRequest = booking.getServiceRequest();
        serviceRequest.setStatus(ServiceRequest.Status.COMPLETED);
        serviceRequestRepository.save(serviceRequest);

        return bookingRepository.save(booking);
    }

//    public Booking completeBooking(Long bookingId, CompletionDto dto, User customer) {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        // Verify that the customer owns this booking
//        if (!booking.getCustomer().getId().equals(customer.getId())) {
//            throw new RuntimeException("Unauthorized access to booking");
//        }
//
//        // Update booking with completion details
//        booking.setStatus(Booking.Status.COMPLETED);
//        booking.setPaymentAmount(dto.getPaymentAmount());
//        booking.setFeedback(dto.getFeedback());
//        booking.setRating(dto.getRating());
//        booking.setCompletedAt(LocalDateTime.now());
//
//        // Update service request status
//        ServiceRequest serviceRequest = booking.getServiceRequest();
//        serviceRequest.setStatus(ServiceRequest.Status.COMPLETED);
//        serviceRequestRepository.save(serviceRequest);
//
//        return bookingRepository.save(booking);
//    }
}