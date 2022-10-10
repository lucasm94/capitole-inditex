package com.inditex.prices.service;

import static com.inditex.prices.constants.Constants.BRAND;
import static com.inditex.prices.constants.Constants.DATE_FORMAT;
import static com.inditex.prices.constants.Constants.EUR_CURR;
import static com.inditex.prices.constants.Constants.NOT_FOUND_MESSAGE;
import static com.inditex.prices.constants.Constants.PRODUCT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.inditex.prices.entity.Brand;
import com.inditex.prices.entity.Prices;
import com.inditex.prices.entity.Product;
import com.inditex.prices.exception.PriceException;
import com.inditex.prices.repository.BrandRepository;
import com.inditex.prices.repository.PriceRepository;
import com.inditex.prices.repository.ProductRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DatabaseService {
  private final PriceRepository priceRepository;
  private final BrandRepository brandRepository;
  private final ProductRepository productRepository;

  @Autowired
  public DatabaseService(PriceRepository priceRepository, BrandRepository brandRepository, ProductRepository productRepository)
      throws ParseException {
    this.priceRepository = priceRepository;
    this.brandRepository = brandRepository;
    this.productRepository = productRepository;
    initDB();
  }

  /**
   * This method saves the prices of the example.
   */
  private void initDB() throws ParseException {
    var zara = Brand.builder().name("ZARA").build();
    this.brandRepository.save(zara);

    var product = Product.builder().id(35455L).build();
    this.productRepository.save(product);

    var dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
    var dateStartPriceOne = LocalDateTime.parse("2020-06-14-00.00.00", dateFormat);
    var dateStartPriceTwo = LocalDateTime.parse("2020-06-14-15.00.00", dateFormat);
    var dateStartPriceThree = LocalDateTime.parse("2020-06-15-00.00.00", dateFormat);
    var dateStartPriceFour = LocalDateTime.parse("2020-06-15-16.00.00", dateFormat);
    var dateEndPriceOne = LocalDateTime.parse("2020-12-31-23.59.59", dateFormat);
    var dateEndPriceTwo = LocalDateTime.parse("2020-06-14-18.30.00", dateFormat);
    var dateEndPriceThree = LocalDateTime.parse("2020-06-15-11.00.00", dateFormat);
    var dateEndPriceFour = LocalDateTime.parse("2020-12-31-23.59.59", dateFormat);

    var priceOne = Prices.builder().brand(zara).startDate(dateStartPriceOne).endDate(dateEndPriceOne)
        .priceList(1L).product(product).priority(0).price(35.50).curr(EUR_CURR).build();
    var priceTwo = Prices.builder().brand(zara).startDate(dateStartPriceTwo).endDate(dateEndPriceTwo)
        .priceList(2L).product(product).priority(1).price(25.45).curr(EUR_CURR).build();
    var priceThree = Prices.builder().brand(zara).startDate(dateStartPriceThree).endDate(dateEndPriceThree)
        .priceList(3L).product(product).priority(1).price(30.50).curr(EUR_CURR).build();
    var priceFour = Prices.builder().brand(zara).startDate(dateStartPriceFour).endDate(dateEndPriceFour)
        .priceList(4L).product(product).priority(1).price(38.50).curr(EUR_CURR).build();

    this.priceRepository.save(priceOne);
    this.priceRepository.save(priceTwo);
    this.priceRepository.save(priceThree);
    this.priceRepository.save(priceFour);
  }

  /**
   * This method validates if the brand exists.
   */
  public void validateExistsBrand(Long brandId) {
    if (this.brandRepository.findById(brandId).isEmpty()) {
      log.error(String.format("[validate brand][brandId:%d] Not found", brandId));
      throw new PriceException(String.format(NOT_FOUND_MESSAGE, BRAND, brandId), NOT_FOUND);
    }
  }

  /**
   * This method validates if the product exists.
   */
  public void validateExistsProduct(Long productId) {
    if (this.productRepository.findById(productId).isEmpty()) {
      log.error(String.format("[Validate product][productId:%d] Not found", productId));
      throw new PriceException(String.format(NOT_FOUND_MESSAGE, PRODUCT, productId), NOT_FOUND);
    }
  }
}
