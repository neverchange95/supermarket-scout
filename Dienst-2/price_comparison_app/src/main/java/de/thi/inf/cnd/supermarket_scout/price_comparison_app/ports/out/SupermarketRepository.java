package de.thi.inf.cnd.supermarket_scout.price_comparison_app.ports.out;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Supermarket;

import java.util.List;

public interface SupermarketRepository {
    List<Supermarket> findByLocationIgnoreCaseContaining(String location);
}
