package org.backend.gregsgamesbackend.services;

import org.backend.gregsgamesbackend.dto.request.DiscountDTO;
import org.backend.gregsgamesbackend.models.Discount;
import org.backend.gregsgamesbackend.repository.DiscountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscountService {


    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public Discount updateDiscount(int id, DiscountDTO dto) {
        Optional<Discount> discount = discountRepository.findById(id);
        if (!discount.isPresent()) {
            throw new RuntimeException("Discount not found!");
        }
        Discount updatedDiscount = discount.get();
        updatedDiscount.setDiscountName(dto.getName());
        updatedDiscount.setAmount(dto.getAmount());
        return discountRepository.save(updatedDiscount);
    }

    public void deleteDiscount(int id) {
        if (!discountRepository.existsById(id)) {
            throw new RuntimeException("Discount not found");
        }
        discountRepository.deleteById(id);
    }

    public Discount createDiscount(DiscountDTO dto) {
        Discount newDiscount = new Discount();
        newDiscount.setDiscountName(dto.getName());
        newDiscount.setAmount(dto.getAmount());
        return discountRepository.save(newDiscount);
    }
}
