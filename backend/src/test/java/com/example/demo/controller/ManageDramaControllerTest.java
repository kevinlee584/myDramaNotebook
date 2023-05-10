package com.example.demo.controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import com.example.demo.model.Drama;
import com.example.demo.dto.DramaRequestBody;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;


class ManageDramaControllerTest {

    private MockMvc mvc;
    private UserService userService;

    final private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        userService = mock(UserService.class);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        mvc = MockMvcBuilders.standaloneSetup(new ManageDramaController(userService)).setMessageConverters(converter).build();
    }


    @Test
    public void shouldSaveDrama() throws Exception {
        when(userService.saveDrama("test", "test"))
                .thenReturn(new DramaRequestBody("test", "test"));

        when(userService.getRecord())
                .thenReturn(List.of(new Drama("test", "test", "testUrl", "testUrl")));

        InOrder inOrder = Mockito.inOrder(userService);


        String reqBody = objectMapper.writeValueAsString(new DramaRequestBody("test", "test"));
        String resBody = objectMapper.writeValueAsString(new Drama("test", "test", "testUrl", "testUrl"));

        mvc.perform(post("/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(reqBody));

        mvc.perform(get("/user/record"))
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("[%s]", resBody)));

        inOrder.verify(userService).saveDrama("test", "test");
        inOrder.verify(userService).getRecord();

    }

    @Test
    public void shouldRemoveDrama() throws Exception {
        when(userService.saveDrama("test", "test"))
                .thenReturn(new DramaRequestBody("test", "test"));

        when(userService.getRecord())
                .thenReturn(Collections.EMPTY_LIST);

        InOrder inOrder = Mockito.inOrder(userService);

        String reqBody = objectMapper.writeValueAsString(new DramaRequestBody("test", "test"));

        mvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(reqBody));

        mvc.perform(delete("/user/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody))
                .andExpect(status().isNoContent());

        mvc.perform(get("/user/record"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        inOrder.verify(userService).saveDrama("test", "test");
        inOrder.verify(userService).removeDrama("test", "test");
        inOrder.verify(userService).getRecord();

    }
}