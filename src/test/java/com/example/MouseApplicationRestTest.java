package com.example;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MouseApplicationRestTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void should_put_resource_and_get_it_via_location() throws Exception {
        mvc.perform(put("/mice/mickey").content(
                new ObjectMapper().writeValueAsString(ImmutableMap.of(
                        "name", "mickey"
                        ))
        ))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", endsWith("/mice/mickey")))
        .andDo((putMouse) -> {
            // follow the location header.
            mvc.perform(get(putMouse.getResponse().getHeader("Location")))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("name", is("mickey")))
            .andExpect(jsonPath("_id", notNullValue()));
        });
    }

    @Test
    public void should_update_age() throws Exception {
        mvc.perform(put("/mice/bernhard").content(
                new ObjectMapper().writeValueAsString(ImmutableMap.of(
                        "name", "bernhard",
                        "age", 10
                        ))
        ))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", endsWith("/mice/bernhard")))
        .andDo((putMouse) -> {
            String jsonResponse = putMouse.getResponse().getContentAsString();
            String oldId = new ObjectMapper().readTree(jsonResponse).get("_id").asText();
            mvc.perform(put("/mice/bernhard").content(new ObjectMapper().writeValueAsString(ImmutableMap.of(
                    "name", "bernhard",
                    "age", 12
                    ))))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("age", is(12)))
            .andExpect(jsonPath("_id", is(not(oldId))));
        });
        mvc.perform(get("/mice"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("_embedded.mice", hasSize(1)));
    }

    @Test
    public void should_not_update_mouse_id() throws Exception {
        // given
        mvc.perform(put("/mice/minney").content(
                new ObjectMapper().writeValueAsString(ImmutableMap.of(
                        "name", "minney"
                        ))
        ))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", endsWith("/mice/minney")))
        .andDo((putMouse) -> {
            // follow the location header.
            mvc.perform(get(putMouse.getResponse().getHeader("Location")))
            .andDo(print())
            .andExpect(status().isOk());
        })
        .andDo((putMouse) -> {
            // follow the location header. Have to use PUT as 
            // on HTTP PATCH method there is no Location header.. ?!
            mvc.perform(put(putMouse.getResponse().getHeader("Location"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"minney2\",\"_id\":\"00000000-07b0-11e6-9f1b-a7ea756fd6b5\"}"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string("Location", endsWith("/mice/minney2")))
            .andDo((patchMouse) -> {
                mvc.perform(get(patchMouse.getResponse().getHeader("Location")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_id", is(not("00000000-07b0-11e6-9f1b-a7ea756fd6b5"))));
            });
        });
    }

}
