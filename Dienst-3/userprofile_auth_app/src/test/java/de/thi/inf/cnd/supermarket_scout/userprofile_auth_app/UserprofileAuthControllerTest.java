package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserprofileAuthAppApplication.class)
@AutoConfigureMockMvc
public class UserprofileAuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testRegisterUser() throws Exception {
        String jsonBody = """
        {
          "username": "Test",
          "password": "geheimesPasswort",
          "location": "Berlin",
          "email": "test@mustermann.de",
          "preferredCategory": "Sonstiges"
        }
        """;

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/user-service/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.errorMessage").isEmpty())
                .andExpect(jsonPath("$.user.username").value("Test"))
                .andExpect(jsonPath("$.user.password").doesNotExist())
                .andExpect(jsonPath("$.user.id").doesNotExist());

    }

    public void testLoginUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/user-service/login")
                .param("username", "Test")
                .param("password", "geheimesPasswort")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.errorMessage").isEmpty())
                .andExpect(jsonPath("$.user.username").value("Test"))
                .andExpect(jsonPath("$.user.password").doesNotExist())
                .andExpect(jsonPath("$.user.id").doesNotExist());
    }
}
