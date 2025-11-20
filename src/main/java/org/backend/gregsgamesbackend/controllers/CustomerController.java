package org.backend.gregsgamesbackend.controllers;

import org.backend.gregsgamesbackend.dto.request.CustomerSyncRequest;
import org.backend.gregsgamesbackend.models.Customer;
import org.backend.gregsgamesbackend.services.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*") // to allow frontend, whatever port its requesting from
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // When a user signs in with Clerk on the frontend,
    // send their info here to sync with your DB.
    @PostMapping("/sync")
    public Customer syncCustomer(@RequestBody CustomerSyncRequest request) {
        return customerService.findOrCreateCustomer(
                request
        );
    }
}
