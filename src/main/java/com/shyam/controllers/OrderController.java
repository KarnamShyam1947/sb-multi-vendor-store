package com.shyam.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shyam.dto.OrderDTO;
import com.shyam.entities.OrderEntity;
import com.shyam.entities.ProductEntity;
import com.shyam.entities.UserEntity;
import com.shyam.services.OrderService;
import com.shyam.services.ProductService;
import com.shyam.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/order/{id}")
    public String order(@PathVariable int id, Model model, @ModelAttribute("order") OrderDTO order) {
        ProductEntity product = productService.getProductById(id);
        model.addAttribute("product", product);
        System.out.println(id);
        
        return "customer/order";
    }
    
    @PostMapping("/order/{id}")
    public String processOrder(
        @PathVariable int id, 
        Model model, 
        @Valid @ModelAttribute("order") OrderDTO order, 
        BindingResult result, 
        Principal principal
    ) {
        if (result.hasErrors()) {
            ProductEntity product = productService.getProductById(id);
            model.addAttribute("product", product);
            return "customer/order";
        }
        
            
        UserEntity user = userService.getUserByEmail(principal.getName());

        orderService.makeOrder(id, user.getId(), (int) order.getQuantity(), order.getModeOfPayment());
        return "redirect:/";
    }

    @GetMapping("/customer/orders")
    public String customerOrders(Principal principal, Model model) {
        UserEntity user = userService.getUserByEmail(principal.getName());
        List<OrderEntity> orders = orderService.getCustomerOrders(user.getId());
        model.addAttribute("orders", orders);

        return "customer/customer-orders";
    }
    
    @GetMapping("/vender/orders")
    public String venderOrders(Principal principal, Model model) {
        UserEntity user = userService.getUserByEmail(principal.getName());
        List<OrderEntity> orders = orderService.getVenderOrders(user.getId());
        model.addAttribute("orders", orders);

        return "vendor/vender-orders";
    }
}
