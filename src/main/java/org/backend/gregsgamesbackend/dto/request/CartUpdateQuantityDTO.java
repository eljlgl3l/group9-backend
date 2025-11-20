package org.backend.gregsgamesbackend.dto.request;

public class CartUpdateQuantityDTO {
    private String clerkId;
    private Long productId;
    private Integer change; // Change amount (+1 or -1)

    public String getClerkId() {
        return clerkId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
        this.change = change;
    }
}