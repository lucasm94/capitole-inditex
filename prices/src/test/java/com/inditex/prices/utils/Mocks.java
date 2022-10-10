package com.inditex.prices.utils;

import static com.inditex.prices.constants.Constants.DATE_FORMAT;
import static com.inditex.prices.constants.Constants.EUR_CURR;

import com.inditex.prices.entity.Brand;
import com.inditex.prices.entity.Prices;
import com.inditex.prices.entity.Product;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Mocks {
  public static final Long BRAND_ID = 1L;
  public static final Long INVALID_BRAND_ID = -1L;
  public static final Long NOT_FOUND_BRAND_ID = 2L;
  public static final Long PRODUCT_ID = 1L;
  public static final Long INVALID_PRODUCT_ID = -1L;
  public static final Long NOT_FOUND_PRODUCT_ID = 2L;

  public static Brand getBrand() {
    return Brand.builder().id(1L).name("ZARA").build();
  }

  public static Product getProduct() {
    return Product.builder().id(1L).name("Product").build();
  }

  public static Prices getPrice(LocalDateTime startDate, LocalDateTime endDate) {
    return Prices.builder().brand(getBrand()).startDate(startDate)
        .endDate(endDate).priceList(1L).product(getProduct())
        .priority(0).price(35.50).curr(EUR_CURR).build();
  }
  public static List<Prices> getPricesFilterByDate() {
    var dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
    return List.of(getPrice(LocalDateTime.parse("2020-06-14-00.00.00", dateFormat),
        LocalDateTime.parse("2020-12-31-23.59.59", dateFormat)));
  }

  public static List<Prices> getPrices() {
    var dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
    var dateStartPriceOne = LocalDateTime.parse("2020-06-14-00.00.00", dateFormat);
    var dateEndPriceOne = LocalDateTime.parse("2020-12-31-23.59.59", dateFormat);
    var dateStartPriceTwo = LocalDateTime.parse("2020-06-14-15.00.00", dateFormat);
    var dateEndPriceTwo = LocalDateTime.parse("2020-06-14-18.30.00", dateFormat);
    return List.of(getPrice(dateStartPriceOne, dateEndPriceOne), getPrice(dateStartPriceTwo, dateEndPriceTwo));
  }
}
