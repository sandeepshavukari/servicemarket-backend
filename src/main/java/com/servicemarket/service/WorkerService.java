package com.servicemarket.service;

import com.servicemarket.dto.QuoteDto;
import com.servicemarket.entity.*;
import com.servicemarket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkerService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<ServiceRequest> getAvailableRequests(User worker) {
        return serviceRequestRepository.findAvailableRequestsForWorker(worker);
    }

    public Quote submitQuote(QuoteDto dto, User worker) {
        // Find the service request
        ServiceRequest serviceRequest = serviceRequestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new RuntimeException("Service request not found"));

        // Check if service request is still open
        if (serviceRequest.getStatus() != ServiceRequest.Status.OPEN) {
            throw new RuntimeException("Service request is no longer available for quotes");
        }

        // Check if worker has already submitted a quote for this request
        if (quoteRepository.existsByWorkerAndServiceRequest(worker, serviceRequest)) {
            throw new RuntimeException("You have already submitted a quote for this request");
        }

        // Create and save quote
        Quote quote = new Quote();
        quote.setWorker(worker);
        quote.setServiceRequest(serviceRequest);
        quote.setPrice(dto.getPrice());
        quote.setDescription(dto.getDescription());
        quote.setEstimatedDuration(dto.getEstimatedDuration());

        return quoteRepository.save(quote);
    }

    public List<Booking> getWorkerBookings(User worker) {
        return bookingRepository.findByWorkerOrderByCreatedAtDesc(worker);
    }
}