package org.backend.gregsgamesbackend.repository;

import org.backend.gregsgamesbackend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByClerkId(String clerkId);
    Optional<Customer> findByEmail(String email);
}
