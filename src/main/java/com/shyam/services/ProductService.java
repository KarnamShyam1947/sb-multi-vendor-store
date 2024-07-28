package com.shyam.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.shyam.dto.ProductDTO;
import com.shyam.entities.ProductEntity;
import com.shyam.entities.UserEntity;
import com.shyam.repositories.ProductRepository;
import com.shyam.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final CloudinaryService cloudinaryService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void addProduct(ProductDTO product, String venderEmail) {
        ProductEntity newProduct = new ProductEntity();

        newProduct.setName(product.getName());
        newProduct.setPrice(product.getTotalPrice());
        newProduct.setDescription(product.getDescription());
        
        @SuppressWarnings("rawtypes")
        Map res = cloudinaryService.uploadToCloud(product.getFile(), "product");
        newProduct.setImageUrl((String) (res.get("secure_url")));
        newProduct.setPublicId((String) (res.get("public_id")));
        
        UserEntity vender = userRepository.findByEmail(venderEmail);
        newProduct.setVender(vender);
        newProduct.setCompanyName(vender.getCompanyName());

        productRepository.save(newProduct);
    }

    public List<ProductEntity> venderProducts(String email) {
        UserEntity vender = userRepository.findByEmail(email);
        List<ProductEntity> products = productRepository.findByVender(vender);

        return products;
    }

    public List<ProductEntity> searchProduct(String product) {
        return productRepository.findByNameContaining(product);
    }

    public ProductEntity getProductById(int id) {
        return productRepository.findById(id);
    }
    
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public List<String> getDistinctCompanies() {
        return productRepository.findDistinctByCompanyName();
    }
   
    public Page<ProductEntity> getProductsWithPages(int offset, int page) {
        PageRequest pageRequest = PageRequest.of(page, offset);
        Page<ProductEntity> pages = productRepository.findAll(pageRequest);
        // pages.getContent(); - content
        // pages.getSize() - current size
        // pages.getTotalPages - total pages
        
        return pages;
    }

    public List<ProductEntity> getProductsByCompanyName(String companyName) {
        return productRepository.findByCompanyName(companyName);
    }
    

}
