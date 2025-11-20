package org.backend.gregsgamesbackend.controllers;

import org.backend.gregsgamesbackend.dto.request.DiscountDTO;
import org.backend.gregsgamesbackend.models.Discount;
import org.backend.gregsgamesbackend.repository.DiscountRepository;
import org.backend.gregsgamesbackend.services.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // to allow frontend, whatever port its requesting from
public class DiscountController {

    private final DiscountRepository discountRepository;
    private final DiscountService discountService;

    public DiscountController(DiscountRepository discountRepository, DiscountService discountService) {
        this.discountRepository = discountRepository;
        this.discountService = discountService;
    }

    @GetMapping("/discounts")
    public List<Discount> getDiscounts() {
        return discountRepository.findAll();
    }

    @GetMapping("/discounts/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable int id) {
        Optional<Discount> discount = discountRepository.findById(id);

        if (discount.isPresent()) {
            return ResponseEntity.ok(discount.get()); // 200 OK with the discount
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PutMapping("/discounts/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable int id, @RequestBody DiscountDTO dto) {
        Discount created = discountService.updateDiscount(id, dto);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/discounts/create")
    public ResponseEntity<Discount> createDiscount(@RequestBody DiscountDTO dto) {
        Discount created = discountService.createDiscount(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/discounts/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable int id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
