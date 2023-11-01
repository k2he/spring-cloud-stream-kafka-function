package com.cloudstreamkafkafunctional.programming.consumeraccountenrichment.service;

import com.cloudstreamkafkafunctional.programming.sharedLib.model.AccountInfo;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.AddressInfo;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionReportData;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

  public AccountInfo enrichAccountInfo(TransactionReportData transactionReportData) {

    //Simulate delay
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    // Enrich recipient AccountInfo
    var recipientAddress = AddressInfo.builder()
        .addressLine("540 Michigan Ave")
        .city("Chicago")
        .province("IL")
        .postalCode("60611").build();

    return AccountInfo.builder()
        .firstName("first name " + transactionReportData.getTranscationId())
        .lastName("last name " + transactionReportData.getTranscationId())
        .acocuntNumber("ACC" + Math.round(Math.random()*1000000))
        .address(recipientAddress)
        .lastUpdated(LocalDateTime.now())
        .build();
  }
}
