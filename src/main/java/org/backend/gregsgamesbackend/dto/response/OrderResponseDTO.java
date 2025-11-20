package org.backend.gregsgamesbackend.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Response DTO for a newly created order
public class OrderResponseDTO {
    private Long orderId;
    private double total;
    private String status;
    private String customerId; // Only send the ID, not the whole Customer object
    // You can optionally include OrderItem DTOs if needed,
    // but without the recursive 'order' field.
    private LocalDateTime date;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}