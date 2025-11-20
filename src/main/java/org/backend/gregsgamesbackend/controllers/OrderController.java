package org.backend.gregsgamesbackend.controllers;

import org.backend.gregsgamesbackend.dto.request.OrderDTO;
import org.backend.gregsgamesbackend.dto.request.OrderItemDTO;
import org.backend.gregsgamesbackend.dto.response.OrderItemResponseDTO;
import org.backend.gregsgamesbackend.dto.response.OrderResponseDTO;
import org.backend.gregsgamesbackend.models.CartItem;
import org.backend.gregsgamesbackend.models.Customer;
import org.backend.gregsgamesbackend.models.CustomerOrder;
import org.backend.gregsgamesbackend.models.OrderItem;
import org.backend.gregsgamesbackend.services.GameService;
import org.backend.gregsgamesbackend.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // to allow frontend, whatever port its requesting from
public class OrderController {

    private final OrderService orderService;
    private final GameService gameService;

    public OrderController(OrderService orderService, GameService gameService) {
        this.orderService = orderService;
        this.gameService = gameService;
    }

    @GetMapping("/orders")
    public List<OrderResponseDTO> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/customer_order")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderDTO request) {
        CustomerOrder created = orderService.createOrder(request);
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId(created.getOrderId());
        responseDTO.setStatus(created.getOrderStatus().toString());
        responseDTO.setTotal(created.getOrderTotal());
        responseDTO.setCustomerId(created.getCustomer().getClerkId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/order_items/bulk")
    public ResponseEntity<List<OrderItemResponseDTO>> createOrderItem(@RequestBody List<OrderItemDTO> itemDtos) {
        // 1. Call the service to process the list
        List<OrderItem> createdItems = orderService.finalizeOrderItemsAndInventory(itemDtos);

        // 2. Convert the list of JPA entities to a list of Response DTOs
        List<OrderItemResponseDTO> response = createdItems.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        // 3. Return a successful 201 response
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Helper method to map the saved entity to a safe response DTO
    private OrderItemResponseDTO convertToResponseDTO(OrderItem entity) {
        // Ensure you create an OrderItemResponseDTO class for outgoing data!
        // This prevents the recursion issue you just fixed.
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setGameId(entity.getProductId());
        dto.setQuantity(entity.getQuantity());
        // ... set other safe fields
        return dto;
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable int id, @RequestBody Map<String, String> requestBody) {
        String newStatus = requestBody.get("status");

        if (newStatus == null) {
            return ResponseEntity.badRequest().build(); // Handle missing status field
        }
        OrderResponseDTO updatedOrder = orderService.updateOrder(id, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}
