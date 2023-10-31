package com.cloudstreamkafkafunctional.programming.sharedLib.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailsInfo {

  private double amount;
  private String currency;
  private String rails;

}
