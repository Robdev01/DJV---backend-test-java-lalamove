package com.desafioLalamove.delivery.service;

import com.desafioLalamove.delivery.dto.CreateOrderRequest;
import com.desafioLalamove.delivery.entity.Order;
import com.desafioLalamove.delivery.enums.OrderStatus;
import com.desafioLalamove.delivery.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada de servico da aplicacao.
 *
 * Aqui ficam as regras de negocio que nao devem ficar espalhadas no controller.
 */
@Service
public class OrderService {
    // Repositorio usado para salvar e consultar pedidos no banco.
    private final OrderRepository repository;

    /**
     * O Spring injeta o repositorio automaticamente.
     */
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    /**
     * Cria um novo pedido.
     *
     * Passos:
     * 1. validar os dados de entrada;
     * 2. montar a entidade;
     * 3. definir dados calculados e status inicial;
     * 4. salvar no banco.
     */
    public Order createOrder(CreateOrderRequest request) {
        // Antes de persistir, garantimos que origem e destino sao validos.
        validateCoordinates(request);

        // Criamos a entidade que representa a linha da tabela no banco.
        Order order = new Order();

        // O contrato atual usa listas no formato [latitude, longitude].
        order.setOriginLat(request.getOrigin().get(0));
        order.setOriginLng(request.getOrigin().get(1));

        order.setDestLat(request.getDestination().get(0));
        order.setDestLng(request.getDestination().get(1));

        // Distancia mockada para simular um calculo real de rota.
        order.setDistance(calculateDistanceMock());

        // Todo pedido novo comeca como nao assumido.
        order.setStatus(OrderStatus.UNASSIGNED);

        return repository.save(order);
    }

    /**
     * Valida se origem e destino existem e possuem exatamente 2 valores.
     */
    private void validateCoordinates(CreateOrderRequest request) {
        if (request.getOrigin() == null || request.getOrigin().size() != 2) {
            throw new RuntimeException("Invalid origin");
        }

        if (request.getDestination() == null || request.getDestination().size() != 2) {
            throw new RuntimeException("Invalid destination");
        }

        // Depois do formato basico, validamos o conteudo numerico.
        validateLatLng(request.getOrigin());
        validateLatLng(request.getDestination());
    }

    /**
     * Valida uma coordenada no formato [latitude, longitude].
     */
    private void validateLatLng(List<String> coords) {
        try {
            Double lat = Double.parseDouble(coords.get(0));
            Double lng = Double.parseDouble(coords.get(1));

            // Latitude valida fica no intervalo de -90 a 90.
            if (lat < -90 || lat > 90) {
                throw new RuntimeException("Invalid latitude");
            }

            // Longitude valida fica no intervalo de -180 a 180.
            if (lng < -180 || lng > 180) {
                throw new RuntimeException("Invalid longitude");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Coordinates must be numbers");
        }
    }

    /**
     * Gera uma distancia ficticia.
     *
     * Esse metodo e temporario e existe apenas para o fluxo do desafio.
     */
    private Integer calculateDistanceMock() {
        return (int) (Math.random() * 10000);
    }

    /**
     * Marca um pedido como TAKEN.
     *
     * Regras:
     * - o pedido precisa existir;
     * - o pedido nao pode ter sido assumido anteriormente.
     */
    public void takeOrder(Long id) {
        Order order = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        // Impede que o mesmo pedido seja assumido mais de uma vez.
        if (order.getStatus() == OrderStatus.TAKEN) {
            throw new RuntimeException("Order already taken");
        }

        order.setStatus(OrderStatus.TAKEN);
        repository.save(order);
    }

    /**
     * Lista pedidos com paginacao.
     *
     * A API recebe page iniciando em 1, mas o Spring Data trabalha
     * internamente com pagina iniciando em 0.
     */
    public List<Order> listOrders(int page, int limit) {
        if (page < 1 || limit < 1) {
            throw new RuntimeException("Invalid pagination");
        }

        PageRequest pageable = PageRequest.of(page - 1, limit);
        Page<Order> result = repository.findAll(pageable);

        return result.getContent();
    }
}
