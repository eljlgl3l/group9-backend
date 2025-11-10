package org.backend.gregsgamesbackend.repository;

import org.backend.gregsgamesbackend.models.Cart;
import org.backend.gregsgamesbackend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findCartByCustomer(Customer customer);
}
