package org.backend.gregsgamesbackend.controllers;

import org.apache.coyote.Response;
import org.backend.gregsgamesbackend.dto.CartAddItemDTO;
import org.backend.gregsgamesbackend.dto.CartUpdateQuantityDTO;
import org.backend.gregsgamesbackend.dto.CustomerSyncRequest;
import org.backend.gregsgamesbackend.models.Cart;
import org.backend.gregsgamesbackend.models.CartItem;
import org.backend.gregsgamesbackend.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*") // to allow frontend, whatever port its requesting from
public class CartController {

    private final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestBody CustomerSyncRequest dto) {
        return cartService.createCart(dto);
    }

    @PostMapping("/add") // ADDING ONLY, NO UPDATE QUANTITY, ONLY CALLED IF ITEM IS NEW
    public ResponseEntity<CartItem> addToCart(@RequestBody CartAddItemDTO dto) {

        // Logic to find Cart by clerkId
        // Logic to find CartItem by productId
        Cart customerCart = cartService.findCartByCustomer(dto.getClerkId());

        // find cartId, (COMPLETED)
        // save cartitem (productId) with that cartid


        // if item exists: increment quantity by dto.getQuantity() (which is 1)
        // else: create new CartItem with quantity 1

        System.out.println("Backend: ADD item " + dto.getProductId() + " for user " + dto.getClerkId() + " quantity " + dto.getQuantity());

        return cartService.saveItemToCart(dto, customerCart);
    }

    // Endpoint for updating the quantity of an existing item
    @PutMapping("/update")
    public ResponseEntity<Void> updateCartItem(@RequestBody CartUpdateQuantityDTO dto) { // probably pass item amount from JS backend

        // Logic to find CartItem by clerkId and productId

        // currentQuantity = currentQuantity + dto.getChange()

        // if (new quantity <= 0) {
        //     Delete the item
        // } else {
        //     Update the quantity
        // }

        System.out.println("Backend: UPDATE item " + dto.getProductId() + " by " + dto.getChange() + " for user " + dto.getClerkId());

        return ResponseEntity.ok().build();
    }
}
