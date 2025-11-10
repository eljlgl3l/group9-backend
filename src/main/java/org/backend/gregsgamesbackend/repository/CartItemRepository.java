package org.backend.gregsgamesbackend.repository;

import org.backend.gregsgamesbackend.models.Cart;
import org.backend.gregsgamesbackend.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart_CartId(Long cartCartId);
}
