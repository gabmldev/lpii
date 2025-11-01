package com.github.gabmldev.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    private String number;

    @Column(name = "holder_name")
    private String holderName;

    @Column(name = "expiry_month")
    private Integer expiryMonth;

    @Column(name = "expiry_year")
    private Integer expiryYear;

    private String cvv;
    private String brand;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = true)
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = true)
    private Profile profile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_address_id", referencedColumnName = "id", nullable = true)
    private PaymentAddress paymentAddress;
}
