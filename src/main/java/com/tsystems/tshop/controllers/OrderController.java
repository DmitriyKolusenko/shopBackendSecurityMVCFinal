package com.tsystems.tshop.controllers;

import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.tsystems.tshop.domain.Client;
import com.tsystems.tshop.domain.Order;
import com.tsystems.tshop.domain.Product;
import com.tsystems.tshop.domain.Products;
import com.tsystems.tshop.services.ClientService;
import com.tsystems.tshop.services.OrderService;
import com.tsystems.tshop.services.ProductService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;

@RestController
@RequestMapping("/api/writeorder")
public class OrderController {
    private final OrderService orderService;
    private final ClientService clientService;
    private final ProductService productService;
    public OrderController(OrderService orderService, ClientService clientService, ProductService productService){
        this.orderService = orderService;
        this.clientService = clientService;
        this.productService = productService;
    }



    @RequestMapping(method = RequestMethod.POST)
    public void writeOrder(@RequestBody Products products){
        Product[] products1 = products.getProducts();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        this.productService.changeStock(products1);
        this.orderService.writeOrder(this.clientService.getClientByName(name).getIdClients(), products1);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/change")
    @ResponseBody
    public Client changeOrders(@RequestBody Order[] orders){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        Client client = this.clientService.getClientByName(name);
        for (Order order: orders){
            order.setClient_Id(client.getIdClients());
            this.orderService.changeDeliveryStat(order);
        }
        return this.clientService.getClientByName(name);
    }
}
