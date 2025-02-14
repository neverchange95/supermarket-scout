package de.thi.inf.cnd.supermarket_scout.price_comparison_app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PriceComparisonAppApplication.class)
@AutoConfigureMockMvc
@Sql(scripts = {"/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class PriceComparisonControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetPriceComparison() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/price-service/compare")
                .param("productName", "Voll")
                .param("location", "Ber")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productName").value("Vollmilch"))
                .andExpect(jsonPath("$[0].supermarketPrices", hasSize(2)))
                .andExpect(jsonPath("$[0].supermarketPrices[0].supermarketLocation").value("Berlin"))
                .andExpect(jsonPath("$[0].supermarketPrices[1].supermarketLocation").value("Berlin"))
                .andExpect(jsonPath("$[0].supermarketPrices[0].supermarketName").value("Edeka"))
                .andExpect(jsonPath("$[0].supermarketPrices[1].supermarketName").value("Rewe"))
                .andExpect(jsonPath("$[0].supermarketPrices[0].effectivePrice").value(1.20))
                .andReturn();
    }

    @Test
    public void testGetCheapestProductByCategoryFromEachSupermarketInLocation() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/price-service/cheapest")
                .param("productCategory", "Milchprodukte")
                .param("location", "Berlin")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].supermarketLocation").value("Berlin"))
                .andExpect(jsonPath("$[1].supermarketLocation").value("Berlin"))
                .andExpect(jsonPath("$[0].productCategory").value("Milchprodukte"))
                .andExpect(jsonPath("$[1].productCategory").value("Milchprodukte"))
                .andExpect(jsonPath("$[1].effectivePrice").value(1.20))
                .andReturn();
    }
}
