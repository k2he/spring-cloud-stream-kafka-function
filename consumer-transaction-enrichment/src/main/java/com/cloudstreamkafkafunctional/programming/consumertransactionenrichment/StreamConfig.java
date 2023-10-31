package com.cloudstreamkafkafunctional.programming.consumertransactionenrichment;

import com.cloudstreamkafkafunctional.programming.consumertransactionenrichment.service.TransactionDetailService;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionDetailsInfo;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionReportData;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class StreamConfig {

  private final TransactionDetailService transactionDetailService;
  @Bean
  public Function<KStream<String, TransactionReportData>, KStream<String, TransactionDetailsInfo>> enrichTransactionDetails() {
    return input -> input
        .mapValues(transactionDetailService::enrichTranscationDetails)
        .peek((key, value) -> log.info("After Enriched : {}", value));
  }
}
