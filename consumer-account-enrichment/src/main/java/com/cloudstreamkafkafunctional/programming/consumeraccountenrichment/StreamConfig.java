package com.cloudstreamkafkafunctional.programming.consumeraccountenrichment;

import com.cloudstreamkafkafunctional.programming.consumeraccountenrichment.service.AccountService;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.AccountInfo;
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

  private final AccountService accountService;
  @Bean
  public Function<KStream<String, TransactionReportData>, KStream<String, AccountInfo>> enrichAccount() {
    return input -> input
//        .peek((key, value) -> log.info("Account {} Before Enriched : {}", key, value))
        .mapValues(accountService::enrichAccountInfo)
        .peek((key, value) -> log.info("After Enriched : {}", value));
  }
}
