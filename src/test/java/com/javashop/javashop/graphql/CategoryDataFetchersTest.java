package com.javashop.javashop.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
class CategoryDataFetchersTest {
    @Autowired
    private CategoryDataFetchers categoryDataFetchers;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        assertThat(categoryDataFetchers).isNotNull();
    }

    @Test
    public void getOrders() throws Exception {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("getCategory.graphql");
        InputStreamReader streamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(streamReader);
        String data = "";
        for (String line; (line = reader.readLine()) != null;) {
            data += line;
        }
        String query = "{\r\n   \"query\": \"" + data + "\"\r\n}";
        RequestBuilder request = MockMvcRequestBuilders.post("/graphql").content(query).contentType("application/json");
        MvcResult result = mockMvc.perform(request).andReturn();
        Integer content = result.getResponse().getStatus();
        assertThat(content).isEqualTo(200);
    }
}