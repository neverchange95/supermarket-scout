package de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.rest;

import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.mapper.PriceMapper;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.rest.dto.CheapestProductDTO;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.adapter.rest.dto.PriceComparisonDTO;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models.Price;
import de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.services.PriceComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/price-service")
public class PriceComparisonController {
    private final PriceComparisonService priceComparisonService;
    private final PriceMapper priceMapper;

    @Autowired
    public PriceComparisonController(PriceComparisonService priceComparisonService, PriceMapper priceMapper) {
        this.priceComparisonService = priceComparisonService;
        this.priceMapper = priceMapper;
    }

    @CrossOrigin
    @GetMapping("/compare")
    public List<PriceComparisonDTO> getPriceComparison(
            @RequestParam("productName") String productName,
            @RequestParam("location") String location
    ){
        List<Price> foundPrices = priceComparisonService.getPriceComparison(productName, location);

        if (foundPrices.isEmpty()) return Collections.emptyList();
        return priceMapper.toPriceCompareDto(foundPrices);
    }

    @CrossOrigin
    @GetMapping("/cheapest")
    public List<CheapestProductDTO> getCheapestProductByCategoryFromEachSupermarketInLocation(
            @RequestParam("productCategory") String productCategory,
            @RequestParam("location") String location
    ) {
        List<Price> foundPrices = priceComparisonService
                .getCheapestProductByCategoryFromEachSupermarketInLocation(
                        productCategory,
                        location
                );

        if (foundPrices.isEmpty()) return Collections.emptyList();
        return priceMapper.toCheapestProductDto(foundPrices);
    }

}
