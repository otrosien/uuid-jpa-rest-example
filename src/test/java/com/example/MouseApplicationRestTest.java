package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MouseApplicationRestTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MouseRepository mouseRepository;

    @Autowired
    private LatestMouseRepository latestMouseRepository;

    @Test
    public void should_put_resource_and_get_it_via_location() throws Exception {
        mvc.perform(put("/mice/mickey").content(
                new ObjectMapper().writeValueAsString(ImmutableMap.of(
                        "name", "mickey"
                        ))
        ))
        // .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", endsWith("/mice/mickey")))
        .andDo((putMouse) -> {
            // follow the location header.
            mvc.perform(get(putMouse.getResponse().getHeader("Location")))
            // .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("name", is("mickey")))
            .andExpect(jsonPath("_id", notNullValue()));
        })
        .andDo((putMouse) -> {
            mvc.perform(delete(putMouse.getResponse().getHeader("Location")))
            // .andDo(print())
            .andExpect(status().isNoContent());
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
        // .andDo(print())
        .andDo(dnc -> {
            then(mouseRepository.findAll()).hasSize(1);
            then(latestMouseRepository.findAll()).hasSize(1);
        })
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", endsWith("/mice/bernhard")))
        .andDo((putMouse) -> {
            String jsonResponse = putMouse.getResponse().getContentAsString();
            String oldId = new ObjectMapper().readTree(jsonResponse).get("_id").asText();
            mvc.perform(put("/mice/bernhard").content(new ObjectMapper().writeValueAsString(ImmutableMap.of(
                    "name", "bernhard",
                    "age", 12
                    ))))
            // .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("age", is(12)))
            .andExpect(jsonPath("_id", is(not(oldId))));
        });
        mvc.perform(get("/mice"))
        // .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("_embedded.mice", hasSize(1)));
    }


}
