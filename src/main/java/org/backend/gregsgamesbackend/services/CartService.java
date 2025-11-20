package org.backend.gregsgamesbackend.services;

import org.backend.gregsgamesbackend.dto.request.CartAddItemDTO;
import org.backend.gregsgamesbackend.dto.request.CustomerSyncRequest;
import org.backend.gregsgamesbackend.models.Cart;
import org.backend.gregsgamesbackend.models.CartItem;
import org.backend.gregsgamesbackend.models.Customer;
import org.backend.gregsgamesbackend.repository.CartItemRepository;
import org.backend.gregsgamesbackend.repository.CartRepository;
import org.backend.gregsgamesbackend.repository.CustomerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class CartService {

    private final CustomerRepository customerRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerService customerService;
    private final CartRepository cartRepository;

    public CartService(CustomerRepository customerRepository, CartItemRepository cartItemRepository, CustomerService customerService, CartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerService = customerService;
        this.cartRepository = cartRepository;
    }

    public ResponseEntity<Cart> createCart(CustomerSyncRequest dto) {
        try {
            Customer customer = customerService.findOrCreateCustomer(dto);
            Optional<Cart> cart = cartRepository.findCartByCustomer(customer);
            Cart newCart = new Cart();
            if (cart.isEmpty()) {
                newCart.setCustomer(customer);
                newCart = cartRepository.save(newCart);
            }

            // Create the Location URI for the new resource (Standard REST Practice)
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{cartid}")
                    .buildAndExpand(newCart.getCartId()) // Use the generated ID
                    .toUri();

            // Return the 201 Created Status
            // HTTP 201 is the standard for successful resource creation.
            return ResponseEntity
                    .created(location) // Sets the 201 Created status and the Location header
                    .body(newCart);

        } catch (Exception e) {
            // Catch any unexpected server errors during the save process
            // Return 500 Internal Server Error
            System.err.println("Error creating cart: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    public Cart findCartByCustomer(String clerkId) {
        Optional<Customer> existing = customerRepository.findByClerkId(clerkId);
        Cart cart = null;
        if (existing.isPresent()) {
            cart = existing.get().getCart();
        }

        return cart;
    }

    public ResponseEntity<CartItem> saveItemToCart(CartAddItemDTO dto, Cart cart) {
        try {
            // Map DTO to new cartItem entity
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProductId(dto.getProductId());
            newItem.setProductType(CartItem.ProductType.GAME);
            // save to db using jpa
            CartItem itemSaved = cartItemRepository.save(newItem);

            // Create the Location URI for the new resource (Standard REST Practice)
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(itemSaved.getCartItemId()) // Use the generated ID
                    .toUri();

            // Return the 201 Created Status
            // HTTP 201 is the standard for successful resource creation.
            return ResponseEntity
                    .created(location) // Sets the 201 Created status and the Location header
                    .body(itemSaved);

        } catch (DataIntegrityViolationException e) {
            // Handle database-specific errors (trying to save a product ID that doesn't exist)
            // Return 400 Bad Request to indicate client sent bad data
            System.err.println("Data integrity violation: " + e.getMessage());
            return ResponseEntity.badRequest().build();

        } catch (Exception e) {
            // Catch any other unexpected server errors during the save process
            // Return 500 Internal Server Error
            System.err.println("Error saving item to cart: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


//    public List<CartItem> findCartItemsByCartId(String clerkId) {
//        Cart cart = findCartByCustomer(clerkId);
//        List<CartItem> cartItems = cartItemRepository.findByCart_CartId(cart.getCartId());
//
//    }
}
