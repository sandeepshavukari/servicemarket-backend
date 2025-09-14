package com.servicemarket.controller;

import com.servicemarket.dto.BookingDto;
import com.servicemarket.dto.CompletionDto;
import com.servicemarket.dto.ServiceRequestDto;
import com.servicemarket.entity.Booking;
import com.servicemarket.entity.ServiceRequest;
import com.servicemarket.entity.User;
import com.servicemarket.service.AuthService;
import com.servicemarket.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthService authService;

    @GetMapping("/requests")
    public ResponseEntity<?> getMyRequests(Authentication authentication) {
        try {
            User customer = authService.getCurrentUser(authentication.getName());
            List<ServiceRequest> requests = customerService.getCustomerRequests(customer);
            return ResponseEntity.ok(requests);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
    @GetMapping("/requests/{requestId}")
    public ResponseEntity<?> getRequestById(@PathVariable Long requestId, Authentication authentication) {
        try {
            User customer = authService.getCurrentUser(authentication.getName());
            ServiceRequest request = customerService.getRequestById(requestId);

            // Check if the request belongs to the authenticated customer
            if (!request.getCustomer().getId().equals(customer.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse("Access denied to this request"));
            }

            return ResponseEntity.ok(request);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/requests")
    public ResponseEntity<?> createServiceRequest(@Valid @RequestBody ServiceRequestDto dto, 
                                                 Authentication authentication) {
        try {
            User customer = authService.getCurrentUser(authentication.getName());
            ServiceRequest request = customerService.createServiceRequest(dto, customer);
            return ResponseEntity.ok(request);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> getMyBookings(Authentication authentication) {
        try {
            User customer = authService.getCurrentUser(authentication.getName());
            List<Booking> bookings = customerService.getCustomerBookings(customer);
            return ResponseEntity.ok(bookings);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingDto dto, 
                                          Authentication authentication) {
        try {
            User customer = authService.getCurrentUser(authentication.getName());
            Booking booking = customerService.createBooking(dto, customer);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/bookings/{bookingId}/complete")
    public ResponseEntity<?> completeBooking(@PathVariable Long bookingId, 
                                           @Valid @RequestBody CompletionDto dto,
                                           Authentication authentication) {
        try {
            User customer = authService.getCurrentUser(authentication.getName());
            Booking booking = customerService.completeBooking(bookingId, dto, customer);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    // Helper class for error responses
    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}