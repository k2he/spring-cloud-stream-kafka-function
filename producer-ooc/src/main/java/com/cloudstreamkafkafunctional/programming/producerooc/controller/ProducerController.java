package com.cloudstreamkafkafunctional.programming.producerooc.controller;

import static java.util.stream.Collectors.toList;

import com.cloudstreamkafkafunctional.programming.producerooc.dto.ProcessTransaction;
import com.cloudstreamkafkafunctional.programming.sharedLib.enums.TransferType;
import com.cloudstreamkafkafunctional.programming.sharedLib.model.TransactionReportData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.stream.function.StreamBridge;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka/v1")
public class ProducerController {

  private final StreamBridge streamBridge;

  @PostMapping("/transcation-enrichment")
  public ResponseEntity<TransactionReportData> startProcess(@RequestBody ProcessTransaction transaction) {
    log.info("Start Processing Single Transaction Enrichment at {}", LocalDateTime.now());

    var reportData = TransactionReportData.builder()
        .tracebilityId(UUID.randomUUID().toString())
        .transcationId(transaction.getTranscationId())
        .transferType(transaction.getTransactionType())
        .createdOn(LocalDateTime.now())
        .build();

    streamBridge.send("producer-out-0", reportData);

    return new ResponseEntity<>(reportData, HttpStatus.OK);
  }

  @GetMapping("/transcation-enrichment/{numOfTransaction}")
  public ResponseEntity<List<TransactionReportData>> getActionByApplicationNumber(@PathVariable("numOfTransaction") Integer numOfTranscation) {
    log.info("Start Processing Bulk of Transaction Enrichments at {}", LocalDateTime.now());

    List<TransactionReportData> allTranscations = IntStream.range(0, numOfTranscation)
        .mapToObj(i -> TransactionReportData.builder()
            .tracebilityId(UUID.randomUUID().toString())
            .transcationId(String.valueOf(i))
            .transferType(i%3 == 0 ? TransferType.SWIFT: TransferType.NON_SWIFT)
            .createdOn(LocalDateTime.now())
            .build())
        .collect(toList());

    allTranscations.stream().forEach(transcationData -> {
          Message<TransactionReportData> msg = MessageBuilder.withPayload(transcationData)
              .setHeader(KafkaHeaders.MESSAGE_KEY, transcationData.getTracebilityId())
              .build();
          streamBridge.send("producer-out-0", msg);
    });

    log.info("{} Messages send to Kafka.", numOfTranscation);
    return new ResponseEntity<>(allTranscations, HttpStatus.OK);
  }

}
