package com.shyam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shyam.entities.ProductEntity;
import com.shyam.entities.UserEntity;

public interface ProductRepository  extends JpaRepository<ProductEntity, Integer> {

    
    List<ProductEntity> findByCompanyName(String companyName);
    
    List<ProductEntity> findByVender(UserEntity vender);

    // List<ProductEntity> findByCategory(String category);

    // List<ProductEntity> findByCategoryAndCompanyName(String category, String companyName);

    ProductEntity findById(int id);
    
    // @Query("SELECT DISTINCT p.category FROM ProductEntity p")
    // List<String> findDistinctByCategories();

    @Query("SELECT DISTINCT p.companyName FROM ProductEntity p")
    List<String> findDistinctByCompanyName();
    
}
