package de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private Long id;
    private Product product;
    private Supermarket supermarket;
    private BigDecimal regularPrice;
    private BigDecimal discountPrice;
    private LocalDate discountValidFrom;
    private LocalDate discountValidTo;
}
