package com.github.gabmldev.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "payment_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    @OneToOne(mappedBy = "paymentAddress")
    private Payment payment;

}
