package com.cloudstreamkafkafunctional.programming.consumertransactionenrichment.service;

import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionDetailsInfo;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionReportData;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

  @Override
  public TransactionDetailsInfo enrichTranscationDetails(
      TransactionReportData transactionReportData) {
    //Simulate delay
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    double amount = ThreadLocalRandom.current().nextInt(100 * 10, 100 * (1000000 + 1)) / 100d;

    return TransactionDetailsInfo.builder()
        .amount(amount)
        .currency("CAD")
        .rails(new Random().nextBoolean() ? "Wire": "Visa")
        .lastUpdated(LocalDateTime.now())
        .build();
  }
}
