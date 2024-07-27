package com.shyam.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shyam.entities.ProductEntity;
import com.shyam.services.ProductService;


@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String homePage(Model model) {

        List<ProductEntity> products = productService.getAllProducts();
        model.addAttribute("products", products);
        
        return "home";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        List<ProductEntity> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "pages/shop";
    }
    
    @GetMapping("/product/{product-id}")
    public String product(
        @PathVariable("product-id") int productId,
        Model model
    ) {
        ProductEntity product = productService.getProductById(productId);
        model.addAttribute("product", product);

        return "pages/product-details";
    }
 
    @GetMapping("/auth")
    @ResponseBody
    public Principal auth(Principal p) {
        return p;
    }
    
    @GetMapping("/about")
    @ResponseBody
    public Principal about(Principal p) {
        return p;
    }
}
