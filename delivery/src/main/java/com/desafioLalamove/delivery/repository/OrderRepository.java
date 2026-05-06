package com.desafioLalamove.delivery.repository;

import com.desafioLalamove.delivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    }
