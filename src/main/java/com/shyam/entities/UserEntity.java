package com.shyam.entities;

import java.util.List;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String companyAddress;
    private String companyName;
    private String description;
    private String websiteUrl;

    private String phoneNumber;
    private String username;
    private String password;
    private String address;
    private String email;
    private String role;

    private LocalDateTime expireTime;
    private String uniqueToken;

    @OneToMany(mappedBy = "vender")
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "customer")
    private List<OrderEntity> orders;
    
}
