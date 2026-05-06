package com.desafioLalamove.delivery.entity;

import com.desafioLalamove.delivery.enums.OrderStatus;
import jakarta.persistence.*;

/**
 * Entidade JPA que representa um pedido salvo no banco de dados.
 */
@Entity
// Usamos "orders" porque "order" pode ser palavra reservada no banco.
@Table(name = "orders")
public class Order {
    // Identificador unico do pedido.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Latitude da origem.
    private String originLat;

    // Longitude da origem.
    private String originLng;

    // Latitude do destino.
    private String destLat;

    // Longitude do destino.
    private String destLng;

    // Distancia entre origem e destino.
    private Integer distance;

    // Status salvo como texto no banco.
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Getters e setters permitem acesso aos dados pela aplicacao, JPA e Jackson.

    public Long getId() {
        return id;
    }

    public String getOriginLat() {
        return originLat;
    }

    public void setOriginLat(String originLat) {
        this.originLat = originLat;
    }

    public String getOriginLng() {
        return originLng;
    }

    public void setOriginLng(String originLng) {
        this.originLng = originLng;
    }

    public String getDestLat() {
        return destLat;
    }

    public void setDestLat(String destLat) {
        this.destLat = destLat;
    }

    public String getDestLng() {
        return destLng;
    }

    public void setDestLng(String destLng) {
        this.destLng = destLng;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
