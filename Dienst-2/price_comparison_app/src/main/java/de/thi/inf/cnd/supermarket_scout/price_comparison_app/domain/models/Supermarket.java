package de.thi.inf.cnd.supermarket_scout.price_comparison_app.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supermarket {
    private Long id;
    private String name;
    private String location;
}
