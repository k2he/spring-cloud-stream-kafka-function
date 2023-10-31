package com.cloudstreamkafkafunctional.programming.consumerstreamprocessor;

import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionDetailsInfo;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.AccountInfo;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionReportData;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@Slf4j
public class StreamConfig {

  @Bean
  public BiFunction<KStream<String, TransactionDetailsInfo>, KStream<String, AccountInfo>, KStream<String, TransactionReportData>> transactionJoiner() {
    return (inputStream1, inputStream2) -> {
      ValueJoiner<TransactionDetailsInfo, AccountInfo, TransactionReportData> valueJoiner =
          (eventValue1, eventValue2) -> TransactionReportData.builder()
              .transactionDetails(eventValue1)
              .recipientAccount(eventValue2)
              .build();

      return inputStream1
          .join(
              inputStream2,
              valueJoiner,
              JoinWindows.ofTimeDifferenceWithNoGrace(Duration.of(10, ChronoUnit.SECONDS)),
              StreamJoined.with(Serdes.String(), new JsonSerde<>(TransactionDetailsInfo.class), new JsonSerde<>(AccountInfo.class))
          )
          .peek((key, value) -> log.info("Joined value: {} ", value));
    };
  }

}
