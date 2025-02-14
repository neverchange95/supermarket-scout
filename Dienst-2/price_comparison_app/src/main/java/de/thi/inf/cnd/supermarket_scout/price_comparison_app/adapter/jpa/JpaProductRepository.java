package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaProductRepository extends CrudRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByNameIgnoreCaseContaining(String name);
}
