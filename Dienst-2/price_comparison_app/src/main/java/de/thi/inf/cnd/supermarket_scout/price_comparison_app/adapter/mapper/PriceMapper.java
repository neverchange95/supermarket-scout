package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.mapper;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.jpa.entities.PriceEntity;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.rest.dto.CheapestProductDTO;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.rest.dto.PriceComparisonDTO;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.rest.dto.PricePerSupermarketDTO;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Price;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PriceMapper {
    private final ProductMapper productMapper;
    private final SupermarketMapper supermarketMapper;

    public Price toDomain(PriceEntity entity) {
        if (entity == null) return null;
        return new Price(
            entity.getId(),
            productMapper.toDomain(entity.getProduct()),
            supermarketMapper.toDomain(entity.getSupermarket()),
            entity.getRegularPrice(),
            entity.getDiscountPrice(),
            entity.getDiscountValidFrom(),
            entity.getDiscountValidTo()
        );
    }

    public List<PriceComparisonDTO> toPriceCompareDto(List<Price> prices) {
        if (prices == null || prices.isEmpty()) {
            return Collections.emptyList();
        }

        // group prices by product
        Map<Product, List<Price>> groupedByProduct = prices.stream()
                .collect(Collectors.groupingBy(Price::getProduct));

        List<PriceComparisonDTO> dtoList = new ArrayList<>();

        for (Map.Entry<Product, List<Price>> entry : groupedByProduct.entrySet()) {
            Product product = entry.getKey();
            List<Price> productPrices = entry.getValue();

            PriceComparisonDTO dto = new PriceComparisonDTO();
            dto.setProductName(product.getName());
            dto.setProductCategory(product.getCategory());
            dto.setProductDescription(product.getDescription());

            List<PricePerSupermarketDTO> supermarketPrices = productPrices.stream()
                    .map(price -> {
                        PricePerSupermarketDTO pDto = new PricePerSupermarketDTO();

                        CalculateEffectivePrice(price);

                        pDto.setSupermarketName(price.getSupermarket().getName());
                        pDto.setSupermarketLocation(price.getSupermarket().getLocation());
                        pDto.setEffectivePrice(CalculateEffectivePrice(price));
                        pDto.setRegularPrice(price.getRegularPrice());
                        pDto.setDiscountPrice(price.getDiscountPrice());
                        pDto.setDiscountValidFrom(price.getDiscountValidFrom());
                        pDto.setDiscountValidTo(price.getDiscountValidTo());

                        return pDto;
                    })
                    .sorted(Comparator.comparing(PricePerSupermarketDTO::getEffectivePrice, Comparator.nullsLast(BigDecimal::compareTo)))
                    .collect(Collectors.toList());

            dto.setSupermarketPrices(supermarketPrices);
            dtoList.add(dto);
        }

        return dtoList;
    }

    public List<CheapestProductDTO> toCheapestProductDto(List<Price> prices) {
        List<CheapestProductDTO> dtoList = new ArrayList<>();

        for (Price price : prices) {
            CheapestProductDTO dto = new CheapestProductDTO();
            dto.setSupermarketName(price.getSupermarket().getName());
            dto.setSupermarketLocation(price.getSupermarket().getLocation());
            dto.setProductName(price.getProduct().getName());
            dto.setProductCategory(price.getProduct().getCategory());
            dto.setProductDescription(price.getProduct().getDescription());
            dto.setEffectivePrice(CalculateEffectivePrice(price));
            dtoList.add(dto);
        }
        return dtoList;
    }

    private BigDecimal CalculateEffectivePrice(Price price) {
        BigDecimal effectivePrice = price.getRegularPrice();
        LocalDate today = LocalDate.now();

        if (price.getDiscountPrice() != null &&
                price.getDiscountValidFrom() != null &&
                price.getDiscountValidTo() != null &&
                !today.isBefore(price.getDiscountValidFrom()) &&
                !today.isAfter(price.getDiscountValidTo()) &&
                price.getDiscountPrice().compareTo(price.getRegularPrice()) < 0) {
            effectivePrice = price.getDiscountPrice();
        }

        return effectivePrice;
    }
}
