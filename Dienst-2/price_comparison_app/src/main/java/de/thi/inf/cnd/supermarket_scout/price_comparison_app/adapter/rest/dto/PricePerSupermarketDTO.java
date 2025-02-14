package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PricePerSupermarketDTO {
    private String supermarketName;
    private String supermarketLocation;
    private BigDecimal effectivePrice;
    private BigDecimal regularPrice;
    private BigDecimal discountPrice;
    private LocalDate discountValidFrom;
    private LocalDate discountValidTo;
}
