package com.inditex.prices.controller;

import com.inditex.prices.dto.PriceResponse;
import com.inditex.prices.service.PriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/prices")
@Slf4j
@Api(value = "Prices API")
public class PriceController {
  private final PriceService priceService;

  @Autowired
  public PriceController(PriceService priceService) {
    this.priceService = priceService;
  }

  @GetMapping
  @ApiOperation(value = "Get the prices applied for a brand and product", response = PriceResponse.class)
  public ResponseEntity<PriceResponse> getPrices(@RequestParam Long brandId, @RequestParam Long productId,
                                                 @RequestParam(required = false) String startDate) {
    log.info("[start][method:GetPrices]");
    return ResponseEntity.ok(this.priceService.getPrices(brandId, productId, startDate));
  }
}
