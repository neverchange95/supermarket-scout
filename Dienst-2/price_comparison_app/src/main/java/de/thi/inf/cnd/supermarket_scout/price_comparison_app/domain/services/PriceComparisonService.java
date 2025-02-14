package de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.services;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Price;

import java.util.List;
import java.util.Map;

public interface PriceComparisonService {
    List<Price> getPriceComparison(String productName, String location);
    List<Price> getCheapestProductByCategoryFromEachSupermarketInLocation(String category, String location);
}
