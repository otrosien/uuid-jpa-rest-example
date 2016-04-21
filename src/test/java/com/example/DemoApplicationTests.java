package com.example;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

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
            .andExpect(jsonPath("id", notNullValue()));
        });
    }

    @Test
    public void should_not_update_mouse_id() throws Exception {
        // given
        mvc.perform(put("/mice/minney").content(
                new ObjectMapper().writeValueAsString(ImmutableMap.of(
                        "name", "minney",
                        "id", "aa923a70-07b0-11e6-9f1b-a7ea756fd6b5"
                        ))
        ))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", endsWith("/mice/minney")))
        .andDo((putMouse) -> {
            // follow the location header.
            mvc.perform(get(putMouse.getResponse().getHeader("Location")))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id", is("aa923a70-07b0-11e6-9f1b-a7ea756fd6b5")));
        })
        .andDo((putMouse) -> {
            // follow the location header. Have to use PUT as 
            // on HTTP PATCH method there is no Location header.. ?!
            mvc.perform(put(putMouse.getResponse().getHeader("Location"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"minney2\",\"id\":\"00000000-07b0-11e6-9f1b-a7ea756fd6b5\"}"))
            .andDo(print())
            .andExpect(status().isNoContent())
            .andExpect(header().string("Location", endsWith("/mice/minney2")))
            .andDo((patchMouse) -> {
                mvc.perform(get(patchMouse.getResponse().getHeader("Location")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is("aa923a70-07b0-11e6-9f1b-a7ea756fd6b5")));
            });
        });
    }

}
