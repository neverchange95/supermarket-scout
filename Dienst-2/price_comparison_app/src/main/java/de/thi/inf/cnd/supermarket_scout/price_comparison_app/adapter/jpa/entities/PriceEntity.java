package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "prices")
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProductEntity product;

    @ManyToOne
    private SupermarketEntity supermarket;

    private BigDecimal regularPrice;
    private BigDecimal discountPrice;
    private LocalDate discountValidFrom;
    private LocalDate discountValidTo;
}
