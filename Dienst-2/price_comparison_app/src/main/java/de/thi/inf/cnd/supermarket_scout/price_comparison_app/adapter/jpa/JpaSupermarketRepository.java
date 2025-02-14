package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa.entities.SupermarketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaSupermarketRepository extends CrudRepository<SupermarketEntity, Long> {
    List<SupermarketEntity> findByLocationIgnoreCaseContaining(String locationPart);
}
