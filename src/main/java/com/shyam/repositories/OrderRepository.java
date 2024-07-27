package com.shyam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shyam.entities.OrderEntity;
import com.shyam.entities.UserEntity;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    List<OrderEntity> findByCustomer(UserEntity customer);

    List<OrderEntity> findByVenderId(int venderId);
}
