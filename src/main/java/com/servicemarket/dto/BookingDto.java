package com.servicemarket.dto;

import jakarta.validation.constraints.NotNull;

public class BookingDto {
    @NotNull(message = "Request ID is required")
    private Long requestId;

    @NotNull(message = "Quote ID is required")
    private Long quoteId;

    // Constructors
    public BookingDto() {}

    public BookingDto(Long requestId, Long quoteId) {
        this.requestId = requestId;
        this.quoteId = quoteId;
    }

    // Getters and Setters
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }
}