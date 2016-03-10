package com.example;

import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebIntegrationTest(randomPort=true)
public class DemoApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void restTest() throws Exception {
        mvc.perform(put("/mice/0f629be4-e6cc-11e5-bb3b-3bf5fa6b2828").content(
                new ObjectMapper().writeValueAsString(ImmutableMap.of("name", "My Mouse"))
        ))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", endsWith("/mice/0f629be4-e6cc-11e5-bb3b-3bf5fa6b2828")))
        .andDo(print())
        .andDo((a) -> {
            // follow the location header.
            mvc.perform(get(a.getResponse().getHeader("Location")))
            .andDo(print())
            .andExpect(status().isOk());
        });
    }

}
