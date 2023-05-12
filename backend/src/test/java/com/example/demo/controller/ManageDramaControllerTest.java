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
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

@WebMvcTest(ManageDramaController.class)
class ManageDramaControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    final private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldSaveDrama() throws Exception {
        Drama drama = new Drama("test", "test", "testUrl", "testUrl");
        when(userService.saveDrama("test", "test")).thenReturn("saved");
        when(userService.getRecord()).thenReturn(List.of(drama));
        InOrder inOrder = Mockito.inOrder(userService);

        String reqBody = objectMapper.writeValueAsString(new DramaRequestBody("test", "test"));
        String resBody = objectMapper.writeValueAsString(drama);

        mvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("{ provider: test, name: test } saved"));

        mvc.perform(get("/user/record"))
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("[%s]", resBody)));

        inOrder.verify(userService).saveDrama("test", "test");
        inOrder.verify(userService).getRecord();
    }

    @Test
    public void shouldRemoveDrama() throws Exception {
        when(userService.saveDrama("test", "test")).thenReturn("saved");
        when(userService.getRecord()).thenReturn(Collections.EMPTY_LIST);
        InOrder inOrder = Mockito.inOrder(userService);

        String reqBody = objectMapper.writeValueAsString(new DramaRequestBody("test", "test"));

        mvc.perform(post("/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqBody))
                .andExpect(status().isCreated())
                .andExpect(content().string("{ provider: test, name: test } saved"));

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