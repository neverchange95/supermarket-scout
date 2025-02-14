package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CheapestProductDTO {
    private String supermarketName;
    private String supermarketLocation;
    private String productName;
    private String productCategory;
    private String productDescription;
    private BigDecimal effectivePrice;
}
