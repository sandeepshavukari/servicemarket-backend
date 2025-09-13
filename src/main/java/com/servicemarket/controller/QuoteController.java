package com.servicemarket.controller;

import com.servicemarket.entity.Quote;
import com.servicemarket.repository.QuoteRepository;
import com.servicemarket.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@CrossOrigin(origins = "*")
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getQuotesForRequest(@PathVariable Long requestId) {
        try {
            return serviceRequestRepository.findById(requestId)
                    .map(request -> {
                        List<Quote> quotes = quoteRepository.findByServiceRequestOrderByCreatedAtAsc(request);
                        return ResponseEntity.ok(quotes);
                    })
                    .orElse(ResponseEntity.notFound().build());
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