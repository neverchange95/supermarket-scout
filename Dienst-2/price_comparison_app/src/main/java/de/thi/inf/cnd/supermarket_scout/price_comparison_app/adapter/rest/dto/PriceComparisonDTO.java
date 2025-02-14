package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PriceComparisonDTO {
    private String productName;
    private String productCategory;
    private String productDescription;
    private List<PricePerSupermarketDTO> supermarketPrices;
}
