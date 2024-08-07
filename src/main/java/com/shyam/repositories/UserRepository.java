package com.shyam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shyam.entities.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>  {
    
    UserEntity findByEmail(String email);

    UserEntity findById(int id);
    
    UserEntity findByUniqueToken(String uniqueToken);

    UserEntity findByCompanyName(String companyName);
}