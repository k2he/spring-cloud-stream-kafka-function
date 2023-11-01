package com.cloudstreamkafkafunctional.programming.consumerstreamprocessor.config;

import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionDetailsInfo;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.AccountInfo;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionReportData;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class StreamConfig {

  private final ProcessProperties processProperties;

  /*
   Below code is Inner Join, it won't produce result if any of input stream not getting data.
   */
//  @Bean
//  public BiFunction<KStream<String, TransactionDetailsInfo>, KStream<String, AccountInfo>, KStream<String, TransactionReportData>> transactionJoiner() {
//    return (inputStream1, inputStream2) -> {
//      ValueJoiner<TransactionDetailsInfo, AccountInfo, TransactionReportData> valueJoiner =
//          (eventValue1, eventValue2) -> TransactionReportData.builder()
//              .transactionDetails(eventValue1)
//              .recipientAccount(eventValue2)
//              .build();
//
//      return inputStream1
//          .join(
//              inputStream2,
//              valueJoiner,
//              JoinWindows.ofTimeDifferenceWithNoGrace(Duration.of(10, ChronoUnit.SECONDS)),
//              StreamJoined.with(Serdes.String(), new JsonSerde<>(TransactionDetailsInfo.class), new JsonSerde<>(AccountInfo.class))
//          )
//          .peek((key, value) -> log.info("Joined value: {} ", value));
//    };
//  }

  @Bean
  public Function<
        KStream<String, TransactionReportData>,
        Function<
            KStream<String, TransactionDetailsInfo>,
            Function<KStream<String, AccountInfo>, KStream<String, TransactionReportData>>
        >
      > transactionJoiner() {
    log.info("Start Processing Join.");

    return initialReport -> (
        transectionDetail ->
            account -> {
              // Step 1: Join Transaction Data with Original TransactionReportData
              KStream<String, TransactionReportData> joinWithTransactionData = initialReport
                  .leftJoin(
                      transectionDetail,
                      enrichTransactionReportDataValueJoiner(),
                      getJoinWindowsWithGrace(),
                      StreamJoined.with(
                          Serdes.String(),
                          new JsonSerde<>(TransactionReportData.class),
                          new JsonSerde<>(TransactionDetailsInfo.class)
                      )
                  );

              // Step 2: Join Account Data with joined data on step 1
              return joinWithTransactionData.leftJoin(
                  account,
                  enrichAccountInfoDataValueJoiner(),
                  getJoinWindowsWithGrace(),
                  StreamJoined.with(
                      Serdes.String(),
                      new JsonSerde<>(TransactionReportData.class),
                      new JsonSerde<>(AccountInfo.class)
                  )
              )
              .peek( (key, value) -> log.info("Final Result: {}", value));
            }
        );
  }

  private JoinWindows getJoinWindowsWithGrace() {
    return JoinWindows.ofTimeDifferenceAndGrace(processProperties.getJoinWindowDuration(), processProperties.getGraceWindowDuration());
  }

  private ValueJoiner<TransactionReportData, TransactionDetailsInfo, TransactionReportData> enrichTransactionReportDataValueJoiner() {
    return (reportData, transactionDetail) -> {
      reportData.setTransactionDetails(transactionDetail);
      return reportData;
    };
  }

    private ValueJoiner<TransactionReportData, AccountInfo, TransactionReportData> enrichAccountInfoDataValueJoiner() {
      return (reportData, accountData) -> {
        reportData.setRecipientAccount(accountData);
        return reportData;
      };
    }
}
