package org.backend.gregsgamesbackend.services;

import org.backend.gregsgamesbackend.dto.CustomerSyncRequest;
import org.backend.gregsgamesbackend.models.Customer;
import org.backend.gregsgamesbackend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findOrCreateCustomer(CustomerSyncRequest dto) {
        Optional<Customer> existing = customerRepository.findByClerkId(dto.getClerkId());

        if (existing.isPresent()) {
            return existing.get();
        }

        Customer newCustomer = new Customer();
        newCustomer.setClerkId(dto.getClerkId());
        newCustomer.setUsername(dto.getUsername());
        newCustomer.setEmail(dto.getEmail());


        return customerRepository.save(newCustomer);
    }
}
