package org.backend.gregsgamesbackend.repository;

import org.backend.gregsgamesbackend.models.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Integer> {
}
