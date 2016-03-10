package com.example;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
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

    @Value("${local.server.port}")
    private int port;

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
        Traverson traverson = new Traverson(URI.create("http://localhost:" + port + "/"), MediaTypes.HAL_JSON);
        Link link = traverson.follow("mice").asLink();
        mvc.perform(post(link.expand().getHref()).content(
                new ObjectMapper().writeValueAsString(ImmutableMap.of("name", "My Mouse"))
        ))
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo((a) -> {
            // follow the location header.
            mvc.perform(get(a.getResponse().getHeader("Location")))
            .andDo(print())
            .andExpect(status().isOk());
        });
    }

}
