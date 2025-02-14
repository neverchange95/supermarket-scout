package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa.entities.PriceEntity;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPriceRepository extends CrudRepository<PriceEntity, Long> {
    List<PriceEntity> findByProductIdInAndSupermarketIdIn(List<Long> productId, List<Long> supermarketIds);
    List<PriceEntity> findByProductCategoryAndSupermarketLocationContaining(String productCategory, String supermarketLocation);
}
