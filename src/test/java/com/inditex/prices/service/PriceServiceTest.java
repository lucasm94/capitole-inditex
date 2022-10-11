package com.inditex.prices.service;

import static com.inditex.prices.constants.Constants.DATE_FORMAT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.inditex.prices.exception.PriceException;
import com.inditex.prices.exception.ValidationException;
import com.inditex.prices.repository.PriceRepository;
import com.inditex.prices.utils.Mocks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PriceServiceTest {
  private final PriceRepository priceRepository = mock(PriceRepository.class);
  private final DatabaseService databaseService = mock(DatabaseService.class);
  private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
  private PriceService priceService;

  @BeforeEach
  void setup() {
    this.priceService = new PriceService(priceRepository, databaseService);
  }

  @Test
  void validateMandatoryParametersArePositiveExceptionByBrandId() {
    assertThrows(ValidationException.class, () -> this.priceService.getPrices(Mocks.INVALID_BRAND_ID, Mocks.PRODUCT_ID, ""));
  }

  @Test
  void validateMandatoryParametersArePositiveExceptionByProductId() {
    assertThrows(ValidationException.class, () -> this.priceService.getPrices(Mocks.BRAND_ID, Mocks.INVALID_PRODUCT_ID, ""));
  }

  @Test
  void getPricesBrandIdNotFound() {
    doThrow(new PriceException("Not found", NOT_FOUND)).when(this.databaseService).validateExistsBrand(Mocks.NOT_FOUND_BRAND_ID);
    assertThrows(PriceException.class, () -> this.priceService.getPrices(Mocks.NOT_FOUND_BRAND_ID, Mocks.PRODUCT_ID, ""));
  }

  @Test
  void getPricesProductIdNotFound() {
    doThrow(new PriceException("Not found", NOT_FOUND)).when(this.databaseService).validateExistsProduct(Mocks.NOT_FOUND_PRODUCT_ID);
    assertThrows(PriceException.class, () -> this.priceService.getPrices(Mocks.BRAND_ID, Mocks.NOT_FOUND_PRODUCT_ID, ""));
  }

  @Test
  void getPricesWrongDateException() {
    assertThrows(PriceException.class, () -> this.priceService.getPrices(Mocks.BRAND_ID, Mocks.PRODUCT_ID, "2292@select<html>"));
  }

  @Test
  void getPricesWithStartDate() {
    var date = LocalDateTime.parse("2020-06-14-00.00.00", this.dateFormat);
    when(this.priceRepository.findAllByStartDate(Mocks.BRAND_ID, Mocks.PRODUCT_ID, date))
        .thenReturn(Mocks.getPricesFilterByDate());
    var prices = this.priceService.getPrices(Mocks.BRAND_ID, Mocks.PRODUCT_ID, "2020-06-14-00.00.00");
    var info = prices.getInfo().get(0);

    assertEquals(1, prices.getInfo().size());
    assertEquals(Mocks.BRAND_ID, prices.getBrandId());
    assertEquals(Mocks.PRODUCT_ID, prices.getProductId());
    assertEquals(35.50, info.getPrice());
    assertEquals(1L, info.getPriceList());
  }

  @Test
  void getPricesWithoutStartDate() {
    when(this.priceRepository.findAllByBrandIdAndProductId(Mocks.BRAND_ID, Mocks.PRODUCT_ID))
        .thenReturn(Mocks.getPrices());
    var prices = this.priceService.getPrices(Mocks.BRAND_ID, Mocks.PRODUCT_ID, "");
    var info = prices.getInfo().get(0);

    assertTrue(prices.getInfo().size() > 1);
    assertEquals(Mocks.BRAND_ID, prices.getBrandId());
    assertEquals(Mocks.PRODUCT_ID, prices.getProductId());
    assertEquals(35.50, info.getPrice());
    assertEquals(1L, info.getPriceList());
  }

}