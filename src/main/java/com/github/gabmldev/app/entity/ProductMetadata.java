package com.github.gabmldev.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "product_metadata")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "category")
    private String category;

    @Column(name = "has_stack")
    private boolean hasStack;

    @Column(name = "has_sell_state")
    private boolean hasSellState;

    @OneToOne(mappedBy = "productMetadata")
    private Product product;
}
