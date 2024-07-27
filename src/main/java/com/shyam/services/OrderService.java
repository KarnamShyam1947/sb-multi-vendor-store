package com.shyam.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shyam.entities.OrderEntity;
import com.shyam.entities.ProductEntity;
import com.shyam.entities.UserEntity;
import com.shyam.repositories.OrderRepository;
import com.shyam.repositories.ProductRepository;
import com.shyam.repositories.UserRepository;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void makeOrder(int productId, int customerId, int quantity, String mode) {
        UserEntity customer = userRepository.findById(customerId);
        ProductEntity product = productRepository.findById(productId);

        double totalPrice = quantity * product.getPrice();

        OrderEntity order = new OrderEntity();

        order.setPaymentMode(mode);
        order.setCustomer(customer);
        order.setQuantity(quantity);
        order.setProductId(productId);
        order.setOrderedOn(LocalDate.now());
        order.setTotalAmount(totalPrice);
        order.setProductName(product.getName());
        order.setVenderId(product.getVender().getId());
        order.setStatus("order placed");
        // order.setDeliverDate(LocalDate.now().plusDays(product.getTimeToDeliver()));
        
        if("COD".equals(mode))
            order.setPaid(false);
        
        else
            order.setPaid(true);

        orderRepository.save(order);
    }

    public List<OrderEntity> getCustomerOrders(int customerId) {
        UserEntity customer = userRepository.findById(customerId);

        return orderRepository.findByCustomer(customer);
    }

    public List<OrderEntity> getVenderOrders(int venderId) {
        return orderRepository.findByVenderId(venderId);
    }

}
