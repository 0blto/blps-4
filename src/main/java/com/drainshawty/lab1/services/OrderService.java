package com.drainshawty.lab1.services;

import com.drainshawty.lab1.model.Order;
import com.drainshawty.lab1.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    final OrderRepository orderRepository;

    public Order createOrder(Order order) {
        order.setStatus(Order.Status.CREATED);
        return orderRepository.save(order);
    }

    public void updateOrderStatus(Long id, Order.Status status) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
