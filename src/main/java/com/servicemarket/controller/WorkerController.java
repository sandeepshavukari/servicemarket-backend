package com.servicemarket.controller;

import com.servicemarket.dto.QuoteDto;
import com.servicemarket.entity.Booking;
import com.servicemarket.entity.Quote;
import com.servicemarket.entity.ServiceRequest;
import com.servicemarket.entity.User;
import com.servicemarket.service.AuthService;
import com.servicemarket.service.WorkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/worker")
@CrossOrigin(origins = "*")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private AuthService authService;

    @GetMapping("/requests")
    public ResponseEntity<?> getAvailableRequests(Authentication authentication) {
        try {
            User worker = authService.getCurrentUser(authentication.getName());
            List<ServiceRequest> requests = workerService.getAvailableRequests(worker);
            return ResponseEntity.ok(requests);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/quotes")
    public ResponseEntity<?> submitQuote(@Valid @RequestBody QuoteDto dto, 
                                        Authentication authentication) {
        try {
            User worker = authService.getCurrentUser(authentication.getName());
            Quote quote = workerService.submitQuote(dto, worker);
            return ResponseEntity.ok(quote);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<?> getMyBookings(Authentication authentication) {
        try {
            User worker = authService.getCurrentUser(authentication.getName());
            List<Booking> bookings = workerService.getWorkerBookings(worker);
            return ResponseEntity.ok(bookings);
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