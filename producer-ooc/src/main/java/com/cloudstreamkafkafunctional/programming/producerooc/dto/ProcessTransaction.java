package com.cloudstreamkafkafunctional.programming.producerooc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.cloudstreamkafkafunctional.programming.sharedLib.enums.TransferType;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessTransaction {

  private String transcationId;
  private TransferType transactionType;

}
