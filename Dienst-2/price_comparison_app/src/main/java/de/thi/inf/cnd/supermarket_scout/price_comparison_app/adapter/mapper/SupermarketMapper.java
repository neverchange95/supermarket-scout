package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.mapper;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa.entities.SupermarketEntity;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Supermarket;
import org.springframework.stereotype.Component;

@Component
public class SupermarketMapper {

    public Supermarket toDomain(SupermarketEntity entity) {
        if (entity == null) return null;
        return new Supermarket(
            entity.getId(),
            entity.getName(),
            entity.getLocation()
        );
    }
}
