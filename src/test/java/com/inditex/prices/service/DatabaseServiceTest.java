package com.inditex.prices.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.inditex.prices.entity.Brand;
import com.inditex.prices.entity.Product;
import com.inditex.prices.exception.PriceException;
import com.inditex.prices.repository.BrandRepository;
import com.inditex.prices.repository.PriceRepository;
import com.inditex.prices.repository.ProductRepository;
import com.inditex.prices.utils.Mocks;
import java.text.ParseException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatabaseServiceTest {
  private final PriceRepository priceRepository = mock(PriceRepository.class);
  private final BrandRepository brandRepository = mock(BrandRepository.class);
  private final ProductRepository productRepository = mock(ProductRepository.class);
  private DatabaseService databaseService;

  @BeforeEach
  void setup() throws ParseException {
    this.databaseService = new DatabaseService(priceRepository, brandRepository, productRepository);
  }

  @Test
  void validateExistsBrandOk() {
    when(this.brandRepository.findById(Mocks.BRAND_ID)).thenReturn(Optional.of(Brand.builder().build()));
    assertDoesNotThrow(() -> this.databaseService.validateExistsBrand(Mocks.BRAND_ID));
  }

  @Test
  void validateExistsBrandFail() {
    assertThrows(PriceException.class, () -> this.databaseService.validateExistsBrand(Mocks.NOT_FOUND_BRAND_ID));
  }

  @Test
  void validateExistsProductOk() {
    when(this.productRepository.findById(Mocks.PRODUCT_ID)).thenReturn(Optional.of(Product.builder().build()));
    assertDoesNotThrow(() -> this.databaseService.validateExistsProduct(Mocks.PRODUCT_ID));
  }

  @Test
  void validateExistsProductFail() {
    assertThrows(PriceException.class, () -> this.databaseService.validateExistsProduct(Mocks.NOT_FOUND_PRODUCT_ID));
  }
}