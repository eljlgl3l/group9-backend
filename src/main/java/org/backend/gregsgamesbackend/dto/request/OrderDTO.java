package org.backend.gregsgamesbackend.dto.request;

import java.time.LocalDate;

public class OrderDTO {

    private String userId;
    private Double total;
    // TODO: safe delete date later, it auto sets it in CustomerOrder.java
    private LocalDate date;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
