package com.desafioLalamove.delivery.entity;

import com.desafioLalamove.delivery.enums.OrderStatus;
import jakarta.persistence.*;


public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String originLat;
        private String originLng;

        private String destLat;
        private String destLng;

        private Integer distance;

        @Enumerated(EnumType.STRING)
        private OrderStatus status;

        // GETTERS E SETTERS

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
