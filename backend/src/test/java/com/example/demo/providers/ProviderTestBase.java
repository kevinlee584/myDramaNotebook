package com.example.demo.providers;


import com.example.demo.model.Drama;
import com.example.demo.scraping.ScraperScripts;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.List;


@AllArgsConstructor
public class ProviderTestBase {
//    private ObjectMapper mapper;
//    private MockMvc mockMvc;
    private String provider;
    private List<String> sorts;

    public void providerAndSortShouldExist() throws Exception{
//        String json =  mockMvc.perform(get("/providers"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        List<Map<String, Object>> providers = mapper.readValue(json, List.class);
//
//        Assertions.assertTrue(providers.size() > 0, "Should have provider");
//
//        Optional<Map<String, Object>> prov = providers.stream().filter(e -> e.get("provider").equals(provider)).findFirst();
//
//        Assertions.assertTrue(prov.isPresent());
//
//        Map<String, Object> p = prov.get();
//
//        for (String sort: sorts)
//            Assertions.assertNotNull(((Map<String, String>)p.get("sorts")).get(sort), String.format("Sort: %s not exist in Provider: %s", sort, provider));

        Assertions.assertEquals(1, ScraperScripts.scrapers.size());
        Assertions.assertEquals(ScraperScripts.scrapers.get(0).getProvider().getName(), provider);
        for (var sort: sorts)
            Assertions.assertNotNull(ScraperScripts.scrapers.get(0).getScripts().get(sort));
    }

    public void shouldHaveDramas(WebDriver driver) throws Exception {
        for (String sort: sorts) {
//            String json = mockMvc.perform(get(String.format("/provider/%s/%s", provider, sort)))
//                    .andExpect(status().isOk())
//                    .andReturn()
//                    .getResponse()
//                    .getContentAsString();
//
//            List<Map<String, String>> dramas = mapper.readValue(json, List.class);
//
//            Assertions.assertTrue(dramas.size() > 0, String.format("/provider/%s/%s is EMPTY", provider, sort));

            List<Drama> dramas = ScraperScripts.scrapers.get(0).getScripts().get(sort).apply(driver);
            for(var drama : dramas) {
                Assertions.assertNotNull(drama.getName());
                Assertions.assertFalse(drama.getName().isBlank());
                Assertions.assertTrue(drama.getImageUrl().startsWith("http"));
                Assertions.assertTrue(drama.getVideoUrl().startsWith("http"));
            }
        }
    }
}
