package com.desafioLalamove.delivery.repository;

import com.desafioLalamove.delivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio da entidade Order.
 *
 * Ao herdar de JpaRepository, o Spring Data gera automaticamente
 * operacoes basicas de persistencia para a entidade.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
