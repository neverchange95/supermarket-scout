package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa.entities.PriceEntity;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.mapper.PriceMapper;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Price;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.ports.out.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceRepositoryImpl implements PriceRepository {
    private final JpaPriceRepository jpaPriceRepository;
    private final PriceMapper priceMapper;

    @Autowired
    public PriceRepositoryImpl(JpaPriceRepository jpaPriceRepository, PriceMapper priceMapper) {
        this.jpaPriceRepository = jpaPriceRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public List<Price> findByProductIdInAndSupermarketIdIn(List<Long> productId, List<Long> supermarketIds) {
        List<PriceEntity> priceEntities = jpaPriceRepository
                .findByProductIdInAndSupermarketIdIn(productId, supermarketIds);

        return priceEntities.stream()
                .map(priceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Price> findByProductCategoryAndSupermarketLocationContaining(String productCategory, String supermarketLocation) {
        List<PriceEntity> priceEntities = jpaPriceRepository
                .findByProductCategoryAndSupermarketLocationContaining(productCategory, supermarketLocation);

        return priceEntities.stream()
                .map(priceMapper::toDomain)
                .toList();
    }
}
