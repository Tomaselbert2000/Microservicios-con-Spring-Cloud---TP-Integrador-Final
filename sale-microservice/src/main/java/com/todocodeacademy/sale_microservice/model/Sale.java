package com.todocodeacademy.sale_microservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleID;
    private LocalDateTime timestamp;
    private Long cartID;

    @PrePersist
    private void setTimestamp() {

        this.timestamp = LocalDateTime.now();
    }
}
