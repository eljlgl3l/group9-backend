package org.backend.gregsgamesbackend.repository;

import org.backend.gregsgamesbackend.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    Integer id(Long id);
}
