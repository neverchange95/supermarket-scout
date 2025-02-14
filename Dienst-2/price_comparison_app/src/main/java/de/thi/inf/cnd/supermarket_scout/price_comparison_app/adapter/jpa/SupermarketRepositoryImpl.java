package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.mapper.SupermarketMapper;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Supermarket;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.ports.out.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupermarketRepositoryImpl implements SupermarketRepository {
    private final JpaSupermarketRepository jpaSupermarketRepository;
    private final SupermarketMapper supermarketMapper;

    @Autowired
    public SupermarketRepositoryImpl(JpaSupermarketRepository jpaSupermarketRepository,
                                     SupermarketMapper supermarketMapper) {
        this.jpaSupermarketRepository = jpaSupermarketRepository;
        this.supermarketMapper = supermarketMapper;
    }

    @Override
    public List<Supermarket> findByLocationIgnoreCaseContaining(String location) {
        return jpaSupermarketRepository.findByLocationIgnoreCaseContaining(location)
                .stream()
                .map(supermarketMapper::toDomain)
                .toList();
    }
}
