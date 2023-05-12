package com.example.demo.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AllArgsConstructor
public class ProviderTestBase {
    private ObjectMapper mapper;
    private MockMvc mockMvc;
    private String provider;
    private List<String> sorts;


    public void providerAndSortShouldExist() throws Exception{
        String json =  mockMvc.perform(get("/providers"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Map<String, Object>> providers = mapper.readValue(json, List.class);

        Assertions.assertTrue(providers.size() > 0, "Should have provider");

        Optional<Map<String, Object>> prov = providers.stream().filter(e -> e.get("provider").equals(provider)).findFirst();

        Assertions.assertTrue(prov.isPresent());

        Map<String, Object> p = prov.get();

        for (String sort: sorts)
            Assertions.assertNotNull(((Map<String, String>)p.get("sorts")).get(sort), String.format("Sort: %s not exist in Provider: %s", sort, provider));

    }

    public void shouldHaveDramas() throws Exception {
        for (String sort: sorts) {
            String json = mockMvc.perform(get(String.format("/provider/%s/%s", provider, sort)))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            List<Map<String, String>> dramas = mapper.readValue(json, List.class);

            Assertions.assertTrue(dramas.size() > 0, String.format("/provider/%s/%s is EMPTY", provider, sort));
        }
    }
}
