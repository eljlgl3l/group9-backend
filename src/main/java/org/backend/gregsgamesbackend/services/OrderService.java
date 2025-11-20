package org.backend.gregsgamesbackend.services;

import jakarta.persistence.EntityNotFoundException;
import org.backend.gregsgamesbackend.dto.request.OrderDTO;
import org.backend.gregsgamesbackend.dto.request.OrderItemDTO;
import org.backend.gregsgamesbackend.dto.response.OrderResponseDTO;
import org.backend.gregsgamesbackend.models.Customer;
import org.backend.gregsgamesbackend.models.CustomerOrder;
import org.backend.gregsgamesbackend.models.OrderItem;
import org.backend.gregsgamesbackend.repository.CustomerRepository;
import org.backend.gregsgamesbackend.repository.OrderItemRepository;
import org.backend.gregsgamesbackend.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final GameService gameService;

    public OrderService(CustomerRepository customerRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, GameService gameService) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.gameService = gameService;
    }

    public CustomerOrder createOrder(OrderDTO dto) {
        CustomerOrder order = new CustomerOrder();
        Optional<Customer> customer = customerRepository.findByClerkId(dto.getUserId());
        if (customer.isEmpty()) {
            throw new RuntimeException("Discount not found");
        }
        order.setCustomer(customer.get());
        order.setOrderTotal(dto.getTotal());
        //order.setOrderDate(dto.getDate());


        return orderRepository.save(order);
    }

    public List<OrderItem> createOrderItems(List<OrderItemDTO> itemDtos) {
        // 1. Convert DTOs to JPA Entities
        List<OrderItem> orderItems = itemDtos.stream()
                .map(this::convertToEntity) // Calls a private helper method below
                .collect(Collectors.toList());
        System.out.println(orderItems);

        // 2. Save all entities in a single transaction (bulk insert)
        return orderItemRepository.saveAll(orderItems);
    }

    // Helper method to convert the DTO to the JPA Entity
    private OrderItem convertToEntity(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        // Set fields from DTO
        // Fetch the CustomerOrder entity
        CustomerOrder parentOrder = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + dto.getOrderId()));
        item.setOrder(parentOrder);
        item.setProductType(OrderItem.ProductType.game); // TODO: note this is hardcoded should prolly fix later, DB's are lowercase but on here they were uppercase
        item.setProductId(dto.getGameId());
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getPrice());


        // 🚨 Critical: Fetch the linked Order and Game (ai generated just here for future maybe)
        // item.setOrder(orderRepository.findById(dto.getOrderId()).orElseThrow(...));
        // item.setGame(gameRepository.findById(dto.getGameId()).orElseThrow(...));

        return item;
    }

    public List<OrderResponseDTO> getOrders() {
        List<CustomerOrder> orders = orderRepository.findAll();
        List<OrderResponseDTO> ordersReturn = new ArrayList<>();

        for (CustomerOrder order : orders) {
            OrderResponseDTO dto = new OrderResponseDTO();

            // Assume you have a helper method or logic to map the fields:
            dto.setOrderId(order.getOrderId());
            dto.setTotal(order.getOrderTotal());
            dto.setStatus(order.getOrderStatus().toString());
            // Make sure to handle linked entities safely (as you did before):
            dto.setCustomerId(order.getCustomer().getClerkId());
            dto.setDate(order.getOrderDate());

            // Add the resulting DTO to the return list
            ordersReturn.add(dto);
        }
        return ordersReturn;
    }

    public OrderResponseDTO updateOrder(int id, String newStatus) {
        Optional<CustomerOrder> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new RuntimeException("Customer Order not found");
        }
        CustomerOrder updated = order.get();
        updated.setOrderStatus(CustomerOrder.OrderStatus.valueOf(newStatus));
        orderRepository.save(updated);

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(updated.getOrderId());
        dto.setTotal(updated.getOrderTotal());
        dto.setStatus(String.valueOf(updated.getOrderStatus()));
        dto.setCustomerId(updated.getCustomer().getClerkId());
        return dto;
    }

    @Transactional // <-- This ensures atomicity (all or nothing)
    public List<OrderItem> finalizeOrderItemsAndInventory(List<OrderItemDTO> itemDtos) {
        // 1. Create order items (Database WRITE 1)
        List<OrderItem> createdItems = this.createOrderItems(itemDtos);

        // 2. Update game inventory (Database WRITE 2)
        gameService.updateQuantityFromOrder(itemDtos);

        return createdItems;
    }


//    public List<OrderResponseDTO> getOrders() {
//
//        // 1. Fetch all JPA entities
//        List<CustomerOrder> orders = orderRepository.findAll();
//
//        // 2. Use a stream to map the list of entities to a list of DTOs
//        List<OrderResponseDTO> ordersReturn = orders.stream()
//                .map(this::convertToResponseDTO) // Call a private mapping method
//                .collect(Collectors.toList());
//
//        return ordersReturn;
//    }
//
//    // Private helper method to handle the mapping logic
//    private OrderResponseDTO convertToResponseDTO(CustomerOrder order) {
//        OrderResponseDTO dto = new OrderResponseDTO();
//
//        // Map fields
//        dto.setOrderId(order.getOrderId());
//        dto.setStatus(order.getOrderStatus().toString());
//        dto.setTotal(order.getOrderTotal());
//        dto.setCustomerId(order.getCustomer().getClerkId());
//
//        // Optional: If you need to map a list of OrderItems, you would do it here:
//        // dto.setItems(order.getItems().stream().map(this::mapItemToDTO).collect(Collectors.toList()));
//
//        return dto;
//    }
}
