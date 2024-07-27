package com.shyam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.shyam.dto.ProductDTO;
import com.shyam.entities.ProductEntity;
import com.shyam.services.ProductService;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class VenderController {

    @Autowired
    private ProductService productService;

    @GetMapping("/vender/products")
    public String venderProducts(Model model, Principal principal) {
        List<ProductEntity> products = productService.venderProducts(principal.getName());
        model.addAttribute("products", products);
        
        return "vendor/vender-products";
    }

    @GetMapping("/vender/add-product")
    public String addProduct(@ModelAttribute("product") ProductDTO product) {
        return "vendor/add-product";
    }
    
    @PostMapping("/vender/add-product")
    public String processAddProduct(
            @Valid @ModelAttribute("product") ProductDTO product, 
            BindingResult result,
            Principal principal) {

        System.out.println(result);
        if (result.hasErrors()) 
            return "vendor/add-product";

        productService.addProduct(product, principal.getName());

        return "redirect:/vender/products";
    }
}
