package com.tsystems.tshop.services;

import com.tsystems.tshop.domain.Order;
import com.tsystems.tshop.domain.Product;

public interface OrderService {
    void writeOrder(final Integer client, final Product[] products);
    void changeDeliveryStat(final Order order);
}
