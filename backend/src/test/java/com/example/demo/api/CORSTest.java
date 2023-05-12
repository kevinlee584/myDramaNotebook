package com.example.demo.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.controller.ManageDramaController;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ManageDramaController.class)
public class CORSTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldForbidden() throws Exception {
        mockMvc.perform(options("/user/save")
                .header("Origin", "evil.com"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldPass() throws Exception {
        mockMvc.perform(options("/user/save")
                        .header("Origin", "http://localhost:3000"))
                .andExpect(status().isOk());
    }
}
