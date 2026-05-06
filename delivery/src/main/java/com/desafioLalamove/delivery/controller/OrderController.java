package com.desafioLalamove.delivery.controller;

import com.desafioLalamove.delivery.dto.CreateOrderRequest;
import com.desafioLalamove.delivery.dto.TakeOrderRequest;
import com.desafioLalamove.delivery.entity.Order;
import com.desafioLalamove.delivery.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller responsavel por expor os endpoints HTTP da API.
 *
 * Aqui acontece a comunicacao entre cliente e sistema:
 * - recebe o JSON da requisicao;
 * - chama a camada de servico;
 * - devolve o JSON de resposta.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    // O service centraliza a regra de negocio para manter o controller enxuto.
    private final OrderService service;

    /**
     * O Spring injeta automaticamente a dependencia do service.
     */
    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Cria um novo pedido.
     *
     * O corpo da requisicao precisa trazer origem e destino.
     */
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            // Toda a regra de criacao fica delegada para a camada de servico.
            Order order = service.createOrder(request);

            // Montamos manualmente o JSON de resposta para devolver
            // apenas os campos esperados pelo contrato da API.
            Map<String, Object> response = new HashMap<>();
            response.put("id", order.getId());
            response.put("distance", order.getDistance());
            response.put("status", order.getStatus());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Erros de validacao e regra de negocio viram 400.
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Marca um pedido existente como TAKEN.
     *
     * O id vem na URL e o status esperado vem no corpo.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> takeOrder(
        @PathVariable Long id,
        @RequestBody TakeOrderRequest request
    ) {
        try {
            // A API espera explicitamente o valor TAKEN no payload.
            if (!"TAKEN".equals(request.getStatus())) {
                throw new RuntimeException("Invalid status");
            }

            // O service valida se o pedido existe e se ainda pode ser assumido.
            service.takeOrder(id);

            Map<String, String> response = new HashMap<>();
            response.put("status", "SUCCESS");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Lista pedidos usando paginacao.
     *
     * Os parametros page e limit chegam como String na URL.
     */
    @GetMapping
    public ResponseEntity<?> listOrders(
        @RequestParam String page,
        @RequestParam String limit
    ) {
        try {
            int pageInt;
            int limitInt;

            try {
                // Convertemos os query params para inteiro antes de enviar ao service.
                pageInt = Integer.parseInt(page);
                limitInt = Integer.parseInt(limit);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid page or limit");
            }

            List<Order> orders = service.listOrders(pageInt, limitInt);

            // Transformamos a lista de entidades em uma lista de mapas
            // para controlar exatamente o formato do JSON de retorno.
            List<Map<String, Object>> response = new ArrayList<>();

            for (Order order : orders) {
                Map<String, Object> item = new HashMap<>();
                item.put("id", order.getId());
                item.put("distance", order.getDistance());
                item.put("status", order.getStatus());
                response.add(item);
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());

            return ResponseEntity.badRequest().body(error);
        }
    }
}
