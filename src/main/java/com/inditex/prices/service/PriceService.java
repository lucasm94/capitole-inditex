package com.inditex.prices.service;

import static com.inditex.prices.constants.Constants.DATE_FORMAT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.inditex.prices.dto.InfoPrice;
import com.inditex.prices.dto.PriceResponse;
import com.inditex.prices.entity.Prices;
import com.inditex.prices.exception.PriceException;
import com.inditex.prices.exception.ValidationException;
import com.inditex.prices.repository.PriceRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceService {
  private final PriceRepository priceRepository;
  private final DatabaseService databaseService;
  private final DateTimeFormatter dateFormat;

  @Autowired
  public PriceService(PriceRepository priceRepository, DatabaseService databaseService) {
    this.priceRepository = priceRepository;
    this.databaseService = databaseService;
    this.dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
  }

  /**
   * This method returns the requested Prices from de DB.
   * The parameters brandId and productId are mandatory and the parameter startDate is optional.
   */
  public PriceResponse getPrices(Long brandId, Long productId, String startDate) throws ValidationException {
    try {
      validateMandatoryParametersArePositive(brandId, productId);
      this.databaseService.validateExistsBrand(brandId);
      this.databaseService.validateExistsProduct(productId);
      if (Objects.nonNull(startDate) && !startDate.isEmpty()) {
        var startDateFormatted = LocalDateTime.parse(startDate, this.dateFormat);
        log.info(String.format("[brandId:%d][productId:%d][startDate:%s] Search Prices started", brandId, productId, startDate));
        return castPricesToPriceResponse(brandId, productId, this.priceRepository.findAllByStartDate(brandId, productId, startDateFormatted));
      }
      log.info(String.format("[brandId:%d][productId:%d] Search Prices started", brandId, productId));
      return castPricesToPriceResponse(brandId, productId, this.priceRepository.findAllByBrandIdAndProductId(brandId, productId));
    } catch (DateTimeParseException e) {
      log.error(String.format("[startDate:%s] Failed to convert", startDate));
      throw new PriceException(e.getMessage(), INTERNAL_SERVER_ERROR);
    }
  }

  private PriceResponse castPricesToPriceResponse(Long brandId, Long productId, List<Prices> prices) {
    var infoPriceList = prices.stream().map(price -> InfoPrice.builder()
            .priceList(price.getPriceList()).startDate(price.getStartDate()).endDate(price.getEndDate()).price(price.getPrice()).build())
        .toList();
    return PriceResponse.builder().brandId(brandId).productId(productId).info(infoPriceList).build();
  }

  /**
   * This method validates if mandatory parameters are positive.
   */
  private void validateMandatoryParametersArePositive(Long brandId, Long productId) throws ValidationException {
    if (brandId < 1 || productId < 1) {
      log.error(String.format("[brandId:%d][productId:%d] %s", brandId, productId, "Parameters are not positive"));
      throw new ValidationException("Parameters should be positive");
    }
  }

}
