package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.mapper;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa.entities.ProductEntity;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(ProductEntity entity) {
        if(entity == null) return null;
        return new Product(
            entity.getId(),
            entity.getName(),
            entity.getCategory(),
            entity.getDescription()
        );
    }
}
