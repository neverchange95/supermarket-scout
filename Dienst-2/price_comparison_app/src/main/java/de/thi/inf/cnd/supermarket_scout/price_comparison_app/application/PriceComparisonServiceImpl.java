package de.thi.inf.cnd.supermarket_scout.price_comparison_app.application;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Price;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Product;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Supermarket;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.ports.out.PriceRepository;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.ports.out.ProductRepository;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.ports.out.SupermarketRepository;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.services.PriceComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PriceComparisonServiceImpl implements PriceComparisonService {
    private final ProductRepository productRepository;
    private final SupermarketRepository supermarketRepository;
    private final PriceRepository priceRepository;

    @Autowired
    public PriceComparisonServiceImpl(ProductRepository productRepository,
                                      SupermarketRepository supermarketRepository,
                                      PriceRepository priceRepository
    ) {
        this.productRepository = productRepository;
        this.supermarketRepository = supermarketRepository;
        this.priceRepository = priceRepository;
    }

    @Override
    public List<Price> getPriceComparison(String productName, String location) {
        List<Product> products = productRepository.findAllByNameIgnoreCaseContaining(productName);
        if(products.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> productIds = products.stream()
                .map(Product::getId)
                .toList();

        List<Supermarket> supermarkets = supermarketRepository.findByLocationIgnoreCaseContaining(location);
        if(supermarkets.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> supermarketIds = supermarkets.stream()
                .map(Supermarket::getId)
                .toList();

        List<Price> prices = priceRepository
                .findByProductIdInAndSupermarketIdIn(productIds, supermarketIds);

        if(prices.isEmpty()) return Collections.emptyList();
        return prices;
    }

    @Override
    public List<Price> getCheapestProductByCategoryFromEachSupermarketInLocation(String category, String location) {
        // find all existing prices based on their category and price
        List<Price> prices = priceRepository.findByProductCategoryAndSupermarketLocationContaining(category, location);

        // comparator for cheapest price
        Comparator<Price> priceComparator = Comparator.comparingDouble(p -> {
                    LocalDate today = LocalDate.now();
                    if (p.getDiscountPrice() != null
                            && p.getDiscountValidFrom() != null
                            && p.getDiscountValidTo() != null
                            && !today.isBefore(p.getDiscountValidFrom())
                            && !today.isAfter(p.getDiscountValidTo())
                            && (p.getDiscountPrice().compareTo(p.getRegularPrice()) < 0))
            {
                        return p.getDiscountPrice().doubleValue();
                    } else {
                        return p.getRegularPrice().doubleValue();
                    }
                }
        );

        // group by supermarket name and choose the cheapest one for each supermarket
        Map<String, Price> cheapestPricesPerSupermarket = prices.stream()
                .collect(Collectors.groupingBy(
                        price -> price.getSupermarket().getName(),
                        Collectors.collectingAndThen(
                                Collectors.minBy(priceComparator),
                                optionalPrice -> optionalPrice.orElse(null)
                        )
                ));

        return cheapestPricesPerSupermarket.values().stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}
