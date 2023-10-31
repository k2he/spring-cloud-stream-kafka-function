package com.cloudstreamkafkafunctional.programming.sharedLib.model;

import com.cloudstreamkafkafunctional.programming.sharedLib.enums.TransferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReportData {

  private String transcationId;
  private String tracebilityId;
  private TransferType transferType;
  private AccountInfo recipientAccount;
  private TransactionDetailsInfo transactionDetails;

}
