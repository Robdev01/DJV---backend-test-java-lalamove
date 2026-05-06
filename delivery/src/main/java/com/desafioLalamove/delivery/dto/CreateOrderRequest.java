package com.desafioLalamove.delivery.dto;

import java.util.List;

/**
 * DTO usado para receber os dados de criacao de pedido.
 *
 * DTO significa Data Transfer Object.
 * Ele representa o formato do JSON recebido pela API.
 */
public class CreateOrderRequest {
    // Origem no formato [latitude, longitude].
    private List<String> origin;

    // Destino no formato [latitude, longitude].
    private List<String> destination;

    // Getters e setters sao usados pelo Jackson para montar o objeto a partir do JSON.

    public List<String> getOrigin() {
        return origin;
    }

    public void setOrigin(List<String> origin) {
        this.origin = origin;
    }

    public List<String> getDestination() {
        return destination;
    }

    public void setDestination(List<String> destination) {
        this.destination = destination;
    }

}
