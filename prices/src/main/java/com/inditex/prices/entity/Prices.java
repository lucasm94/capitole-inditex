package com.inditex.prices.entity;

import static com.inditex.prices.constants.Constants.DATE_FORMAT;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@ApiModel("Model Prices")
public class Prices {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(cascade=CascadeType.MERGE)
  @JoinColumn(name = "brand_id")
  private Brand brand;
  @Column(nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  private LocalDateTime startDate;
  @Column
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
  private LocalDateTime endDate;
  @Column(nullable = false)
  private Long priceList;
  @ManyToOne(cascade=CascadeType.MERGE)
  @JoinColumn(name = "product_id")
  private Product product;
  @Column(nullable = false)
  private Integer priority;
  @Column(nullable = false)
  private Double price;
  @Column(nullable = false)
  private String curr;

}
