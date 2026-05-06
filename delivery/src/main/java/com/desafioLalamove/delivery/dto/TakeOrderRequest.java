package com.desafioLalamove.delivery.dto;

/**
 * DTO usado para receber o status enviado no endpoint de assumir pedido.
 */
public class TakeOrderRequest {
    // Espera-se algo como: { "status": "TAKEN" }.
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
