package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.mapper.ProductMapper;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Product;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.ports.out.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductRepositoryImpl(JpaProductRepository jpaProductRepository, ProductMapper productMapper) {
        this.jpaProductRepository = jpaProductRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> findAllByNameIgnoreCaseContaining(String name) {
        return jpaProductRepository.findAllByNameIgnoreCaseContaining(name)
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }
}
