package org.backend.gregsgamesbackend.repository;

import org.backend.gregsgamesbackend.models.OrderItem;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
