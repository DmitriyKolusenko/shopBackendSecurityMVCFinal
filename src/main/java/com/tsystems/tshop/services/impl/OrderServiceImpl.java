package com.tsystems.tshop.services.impl;

import com.tsystems.tshop.domain.Order;
import com.tsystems.tshop.domain.Product;
import com.tsystems.tshop.repositories.OrderRepository;
import com.tsystems.tshop.services.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public void writeOrder(Integer client, Product[] product) {
        orderRepository.writeOrder(client,product);
    }

    @Override
    public void changeDeliveryStat(Order order) {
        orderRepository.changeDeliveryStat(order);
    }
}
