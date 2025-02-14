package de.thi.inf.cnd.supermarket_scout.price_comparison_app.ports.out;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAllByNameIgnoreCaseContaining(String name);
}
