package de.thi.inf.cnd.supermarket_scout.price_comparison_app.ports.out;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Price;

import java.util.List;

public interface PriceRepository {
    List<Price> findByProductIdInAndSupermarketIdIn(List <Long> productId, List<Long> supermarketIds);
    List<Price> findByProductCategoryAndSupermarketLocationContaining(String productCategory, String supermarketLocation);
}
