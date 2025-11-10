package org.backend.gregsgamesbackend.dto;

// Request DTO for syncing customers
public class CustomerSyncRequest {
    private String clerkId;
    private String username;
    private String email;

    public String getClerkId() { return clerkId; }
    public void setClerkId(String clerkId) { this.clerkId = clerkId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
