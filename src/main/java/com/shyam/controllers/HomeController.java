package com.shyam.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shyam.entities.ProductEntity;
import com.shyam.services.ProductService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

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
    public String shop(
        Model model,
        @RequestParam(name = "query", defaultValue = "") String query,
        @RequestParam(name = "page", defaultValue = "0") String pageStr,
        @RequestParam(name = "per-page", defaultValue = "3") String offsetStr
    ) {
        List<ProductEntity> products;
        int page = Integer.parseInt(pageStr);
        int offset = Integer.parseInt(offsetStr);
        Page<ProductEntity> productsWithPages = productService.getProductsWithPages(offset, page);

        if (!query.equals("")) 
            products = productService.searchProduct(query);       
        
        else
            products = productService.getAllProducts();

        model.addAttribute("productPages", productsWithPages);
        model.addAttribute("products", products);
        model.addAttribute("page", page);
        model.addAttribute("query", query);
        
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
