package com.inditex.prices.controller;

import static com.inditex.prices.utils.Mocks.BRAND_ID;
import static com.inditex.prices.utils.Mocks.INVALID_BRAND_ID;
import static com.inditex.prices.utils.Mocks.INVALID_PRODUCT_ID;
import static com.inditex.prices.utils.Mocks.NOT_FOUND_BRAND_ID;
import static com.inditex.prices.utils.Mocks.NOT_FOUND_PRODUCT_ID;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {
  @Autowired
  private MockMvc mvc;
  private String URL_API = "/v1/prices?brandId=%d&productId=%d&startDate=%s";
  private static final Long EXAMPLE_PRODUCT_ID = 35455L;
  @Value("${spring.security.user.name}")
  private String USER;
  @Value("${spring.security.user.password}")
  private String PASS;

  @Test
  void getPricesOfTheFirstExampleDate() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(String.format(URL_API, BRAND_ID, EXAMPLE_PRODUCT_ID, "2020-06-14-10.00.00"))
            .with(user(USER).password(PASS))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand_id").value(BRAND_ID))
        .andExpect(MockMvcResultMatchers.jsonPath("$.info[0].start_date").value("2020-06-14-00.00.00"));
  }

  @Test
  void getPricesOfTheSecondExampleDate() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(String.format(URL_API, BRAND_ID, EXAMPLE_PRODUCT_ID, "2020-06-14-16.00.00"))
            .with(user(USER).password(PASS))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand_id").value(BRAND_ID))
        .andExpect(MockMvcResultMatchers.jsonPath("$.info[0].start_date").value("2020-06-14-00.00.00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.info[1].start_date").value("2020-06-14-15.00.00"));
  }

  @Test
  void getPricesOfTheThirdExampleDate() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(String.format(URL_API, BRAND_ID, EXAMPLE_PRODUCT_ID, "2020-06-14-21.00.00"))
            .with(user(USER).password(PASS))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand_id").value(BRAND_ID))
        .andExpect(MockMvcResultMatchers.jsonPath("$.info[0].start_date").value("2020-06-14-00.00.00"));
  }

  @Test
  void getPricesOfTheFourthExampleDate() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(String.format(URL_API, BRAND_ID, EXAMPLE_PRODUCT_ID, "2020-06-15-10.00.00"))
            .with(user(USER).password(PASS))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand_id").value(BRAND_ID))
        .andExpect(MockMvcResultMatchers.jsonPath("$.info[0].start_date").value("2020-06-14-00.00.00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.info[1].start_date").value("2020-06-15-00.00.00"));
  }

  @Test
  void getPricesOfTheFifthExampleDate() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(String.format(URL_API, BRAND_ID, EXAMPLE_PRODUCT_ID, "2020-06-16-21.00.00"))
            .with(user(USER).password(PASS))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand_id").value(BRAND_ID))
        .andExpect(MockMvcResultMatchers.jsonPath("$.info[0].start_date").value("2020-06-14-00.00.00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.info[1].start_date").value("2020-06-15-16.00.00"));
  }

  @Test
  void getPricesWrongBrandException() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(String.format(URL_API, INVALID_BRAND_ID, EXAMPLE_PRODUCT_ID, ""))
            .with(user(USER).password(PASS))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void getPricesWrongProductException() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(String.format(URL_API, BRAND_ID, INVALID_PRODUCT_ID, ""))
            .with(user(USER).password(PASS))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void getPricesWrongDateException() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(String.format(URL_API, BRAND_ID, EXAMPLE_PRODUCT_ID, "2292@select<html>"))
            .with(user(USER).password(PASS))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is5xxServerError())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void getPricesNotFoundBrandException() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(String.format(URL_API, NOT_FOUND_BRAND_ID, EXAMPLE_PRODUCT_ID, ""))
            .with(user(USER).password(PASS))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void getPricesNotFoundProductException() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get(String.format(URL_API, BRAND_ID, NOT_FOUND_PRODUCT_ID, ""))
            .with(user(USER).password(PASS))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

}